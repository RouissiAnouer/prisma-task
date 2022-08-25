package com.onpier.task.model.response;

import java.util.List;

import com.onpier.task.repository.document.Books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookResponse {

    List<Books> books;

}
