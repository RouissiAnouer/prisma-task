package com.onpier.task.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import com.onpier.task.model.response.UsersResponse;

public interface TaskService {

	ResponseEntity<UsersResponse> getActiveUsers();

	ResponseEntity<UsersResponse> getInactiveUsers();

	ResponseEntity<?> getUsersBorrowedBookByDate(String date, String timeZone) throws ParseException;

	ResponseEntity<?> getAllAvailableBooks();

	ResponseEntity<?> getBooksByUserByRangeOfDate(String user, String startDate, String endDate, String timeZone) throws ParseException;

}
