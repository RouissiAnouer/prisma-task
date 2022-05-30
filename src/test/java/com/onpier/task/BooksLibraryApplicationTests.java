package com.onpier.task;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.onpier.task.model.response.UsersResponse;
import com.onpier.task.service.TaskService;

@SpringBootTest
class BooksLibraryApplicationTests {
	
	@Autowired TaskService taskUser;
	
	@BeforeEach
	public void setup() {
		//check if the service is not null
		assertNotNull(taskUser);
	}

	@Test
	void getActiveUsers() {
		ResponseEntity<UsersResponse> users = taskUser.getActiveUsers();
		//check if the response is not null
		assertNotNull(users);
		//check if the body of the ResponseEntity is not null
		assertNotNull(users.getBody());
		//check if users is not empty, should not be empty
		assertTrue(!users.getBody().getUsers().isEmpty());
	}
	
	@Test
	void getInactiveUsers() {
		ResponseEntity<UsersResponse> users = taskUser.getInactiveUsers();
		//check if the response is not null
		assertNotNull(users);
		//check if the body of the ResponseEntity is not null
		assertNotNull(users.getBody());
		//check if users is empty, should be empty
		assertTrue(users.getBody().getUsers().isEmpty());
	}

}
