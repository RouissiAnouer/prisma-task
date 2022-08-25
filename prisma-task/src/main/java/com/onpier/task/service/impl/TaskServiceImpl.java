package com.onpier.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onpier.task.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onpier.task.model.response.BookResponse;
import com.onpier.task.model.response.UsersResponse;
import com.onpier.task.repository.BooksRepository;
import com.onpier.task.repository.BorrowRepository;
import com.onpier.task.repository.UserRepository;
import com.onpier.task.repository.document.Books;
import com.onpier.task.repository.document.Borrowed;
import com.onpier.task.repository.document.User;
import com.onpier.task.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    RabbitMqSender rabbitMqSender;

    @Override
    public ResponseEntity<UsersResponse> getActiveUsers(int page, int items) throws JsonProcessingException {
        //List<Borrowed> borrowedBooks = borrowRepository.findAll();
        Pageable pageable = PageRequest.of(page, items);
        Page<Borrowed> borrowedBooks = borrowRepository.findAll(pageable);
        List<User> users = getUsersBorrowedBook(borrowedBooks.getContent());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(users);
        rabbitMqSender.send(json);
        return ResponseEntity.ok(UsersResponse.builder().users(users)
                .currentPage(page)
                .nextPage(borrowedBooks.hasNext() ? page + 1 : null)
                .previousPage(borrowedBooks.hasPrevious() ? page - 1 : null)
                .nextPagePresent(borrowedBooks.hasNext())
                .totalPages(borrowedBooks.getTotalPages() - 1)
                .itemsPerPage(items)
                .items(users.size())
                .build());
    }

    private List<User> getUsersBorrowedBook(List<Borrowed> borrowedBooks) {
        List<User> users = new ArrayList<>();
        List<String> names = borrowedBooks.stream().map(Borrowed::getBorrower).distinct().collect(Collectors.toList());
        names.forEach(n -> {
            String[] splitName = n.split(",");
            String name = splitName[0];
            String firstName = splitName[1];
            Optional<User> userOpt = userRepository.findByNameAndFirstName(name, firstName);
            if (userOpt.isPresent()) {
                String pattern = "EEEEE MMMMM yyyy-MM-dd HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "EN"));
                String date = simpleDateFormat.format(userOpt.get().getMemberSince());
                System.out.println(date);
                users.add(userOpt.get());
            }
        });
        return users;
    }

    @Override
    public ResponseEntity<UsersResponse> getInactiveUsers() {
        List<User> users = userRepository.findAll();
        rabbitMqSender.send("getInactiveUsers invoked");
        List<User> inactiveUsers = new ArrayList<>();
        users.forEach(u -> {
            String borrower = u.getName().concat(",").concat(u.getFirstName());
            List<Borrowed> borrowed = borrowRepository.findByBorrower(borrower);
            if (borrowed.isEmpty()) {
                inactiveUsers.add(u);
            }
        });
        return ResponseEntity.ok(UsersResponse.builder().users(inactiveUsers).build());
    }

    @Override
    public ResponseEntity<?> getUsersBorrowedBookByDate(String date, String timeZone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        rabbitMqSender.send("getUsersBorrowedBookByDate invoked");
        TimeZone tt = TimeZone.getTimeZone(ZoneId.of(timeZone));
        sdf.setTimeZone(tt);
        Date startDate = sdf.parse(date);
        List<Borrowed> borrowedByDate = borrowRepository.findByBorrowedFrom(startDate);
        List<User> users = getUsersBorrowedBook(borrowedByDate);
        return ResponseEntity.ok(UsersResponse.builder().users(users).build());
    }

    @Override
    public ResponseEntity<?> getAllAvailableBooks() {
        List<Borrowed> borrowedBooks = borrowRepository.findAll();
        rabbitMqSender.send("getAllAvailableBooks invoked");
        List<String> bookTitles = borrowedBooks.stream().map(Borrowed::getBook).distinct().collect(Collectors.toList());
        List<Books> books = booksRepository.findByTitleNotIn(bookTitles);
        return ResponseEntity.ok(BookResponse.builder().books(books).build());
    }

    @Override
    public ResponseEntity<?> getBooksByUserByRangeOfDate(String user, String startDate, String endDate, String timeZone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        rabbitMqSender.send("getBooksByUserByRangeOfDate invoked");
        TimeZone tz = TimeZone.getTimeZone(ZoneId.of(timeZone));
        sdf.setTimeZone(tz);
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        List<Borrowed> borrowedBooks = borrowRepository.findByBorrowerAndBorrowedFromAfterAndBorrowedToBefore(user, start, end);
        List<String> names = borrowedBooks.stream().map(Borrowed::getBook).distinct().collect(Collectors.toList());
        List<Books> books = booksRepository.findByTitleIn(names);
        return ResponseEntity.ok(BookResponse.builder().books(books).build());
    }

}
