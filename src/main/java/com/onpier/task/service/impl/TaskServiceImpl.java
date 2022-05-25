package com.onpier.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired UserRepository userRepository;
	@Autowired BooksRepository booksRepository;
	@Autowired BorrowRepository borrowRepository;
	
	@Override
	public ResponseEntity<?> getActiveUsers() {
		List<User> users = new ArrayList<>();
		List<Borrowed> borrowedBooks = borrowRepository.findAll();
		List<String> names = borrowedBooks.stream().map(Borrowed::getBorrower).distinct().collect(Collectors.toList());
		names.forEach(n -> {
			String[] splittedName = n.split(",");
			String name = splittedName[0];
			String firstName = splittedName[1];
			Optional<User> userOpt = userRepository.findByNameAndFirstName(name, firstName);
			if (userOpt.isPresent()) {
				users.add(userOpt.get());
			}
		});
		return ResponseEntity.ok(UsersResponse.builder().users(users).build());
	}

	@Override
	public ResponseEntity<?> getInactiveUsers() {
		List<User> users = userRepository.findAll();
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
	public ResponseEntity<?> getBookBorrowedByDate(String date) throws ParseException {
		List<User> users = new ArrayList<>();
		Date borrowedFrom = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		List<Borrowed> borrowedByDate = borrowRepository.findByBorrowedFrom(borrowedFrom);
		List<String> names = borrowedByDate.stream().map(Borrowed::getBorrower).distinct().collect(Collectors.toList());
		names.forEach(n -> {
			String[] splittedName = n.split(",");
			String name = splittedName[0];
			String firstName = splittedName[1];
			Optional<User> userOpt = userRepository.findByNameAndFirstName(name, firstName);
			if (userOpt.isPresent()) {
				users.add(userOpt.get());
			}
		});
		return ResponseEntity.ok(UsersResponse.builder().users(users).build());
	}

	@Override
	public ResponseEntity<?> getAllAvailableBooks() {
		List<Borrowed> borrowedBooks = borrowRepository.findAll();
		List<String> bookTitles = borrowedBooks.stream().map(Borrowed::getBook).distinct().collect(Collectors.toList());
		List<Books> books = booksRepository.findByTitleNotIn(bookTitles);
		return ResponseEntity.ok(BookResponse.builder().books(books).build());
	}

	@Override
	public ResponseEntity<?> getBooksByUserByRangeOfDate(String user, String startDate, String endDate) throws ParseException {
		Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		List<Borrowed> borrowedBooks = borrowRepository.findByBorrowerAndBorrowedFromAfterAndBorrowedToBefore(user, start, end);
		List<String> names = borrowedBooks.stream().map(Borrowed::getBook).distinct().collect(Collectors.toList());
		List<Books> books = booksRepository.findByTitleIn(names);
		return ResponseEntity.ok(BookResponse.builder().books(books).build());
	}

}
