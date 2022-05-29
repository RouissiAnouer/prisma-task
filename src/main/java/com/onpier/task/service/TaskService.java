package com.onpier.task.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

public interface TaskService {

	ResponseEntity<?> getActiveUsers();

	ResponseEntity<?> getInactiveUsers();

	ResponseEntity<?> getUsersBorrowedBookByDate(String date, String timeZone) throws ParseException;

	ResponseEntity<?> getAllAvailableBooks();

	ResponseEntity<?> getBooksByUserByRangeOfDate(String user, String startDate, String endDate, String timeZone) throws ParseException;

}
