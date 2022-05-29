package com.onpier.task.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onpier.task.service.TaskService;

@RestController
@RequestMapping(value = "/api/v1")
public class TaskController {
	
	@Autowired TaskService taskService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/active_users")
	public ResponseEntity<?> getActiveUsers(HttpServletRequest r) {
		return taskService.getActiveUsers();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/inactive_users")
	public ResponseEntity<?> getInactiveUsers(HttpServletRequest r) {
		return taskService.getInactiveUsers();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user_borrower_by_date")
	public ResponseEntity<?> getUsersBorrowedBookByDate(HttpServletRequest r, @RequestParam String date,
														@RequestParam(defaultValue = "CET") String  timeZone) throws ParseException {
		return taskService.getUsersBorrowedBookByDate(date, timeZone);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/available_books")
	public ResponseEntity<?> getAllAvailableBooks(HttpServletRequest r) {
		return taskService.getAllAvailableBooks();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/books_by_user_by_dates")
	public ResponseEntity<?> getBooksByUserByRangeOfDate(HttpServletRequest r, @RequestParam String user, 
			@RequestParam String startDate, 
			@RequestParam String endDate,
			@RequestParam(defaultValue = "CET") String  timeZone) throws ParseException {
		return taskService.getBooksByUserByRangeOfDate(user, startDate, endDate, timeZone);
	}

}
