package com.onpier.task.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

public interface TaskService {

	ResponseEntity<?> getActiveUsers();

	ResponseEntity<?> getInactiveUsers();

	ResponseEntity<?> getBookBorrowedByDate(String date) throws ParseException;

	ResponseEntity<?> getAllAvailableBooks();

	ResponseEntity<?> getBooksByUserByRangeOfDate(String user, String startDate, String endDate) throws ParseException;

}
