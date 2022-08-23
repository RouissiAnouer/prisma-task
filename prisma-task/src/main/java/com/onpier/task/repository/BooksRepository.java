package com.onpier.task.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.onpier.task.repository.document.Books;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
	
	List<Books> findByTitleNotIn(List<String> titles);
	
	List<Books> findByTitleIn(List<String> titles);

}
