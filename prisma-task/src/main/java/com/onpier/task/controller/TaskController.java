package com.onpier.task.controller;


import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onpier.task.service.TaskService;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/api/v1")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(method = RequestMethod.GET, value = "/active_users")
    public ResponseEntity<?> getActiveUsers(HttpServletRequest r, @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int items) throws JsonProcessingException {
        return taskService.getActiveUsers(page, items);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/inactive_users")
    public ResponseEntity<?> getInactiveUsers(HttpServletRequest r) throws JsonProcessingException {
        return taskService.getInactiveUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user_borrower_by_date")
    public ResponseEntity<?> getUsersBorrowedBookByDate(HttpServletRequest r, @RequestParam String date,
                                                        @RequestParam(defaultValue = "CET") String timeZone) throws ParseException, JsonProcessingException {
        return taskService.getUsersBorrowedBookByDate(date, timeZone);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/available_books")
    public ResponseEntity<?> getAllAvailableBooks(HttpServletRequest r) throws JsonProcessingException {
        return taskService.getAllAvailableBooks();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books_by_user_by_dates")
    public ResponseEntity<?> getBooksByUserByRangeOfDate(HttpServletRequest r, @RequestParam String user,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate,
                                                         @RequestParam(defaultValue = "CET") String timeZone) throws ParseException, JsonProcessingException {
        return taskService.getBooksByUserByRangeOfDate(user, startDate, endDate, timeZone);
    }

}
