package com.onpier.task.model.response;

import java.util.List;

import com.onpier.task.repository.document.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UsersResponse {
	
	private List<User> users;

}
