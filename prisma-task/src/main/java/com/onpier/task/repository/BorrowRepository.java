package com.onpier.task.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.onpier.task.repository.document.Borrowed;

@Repository
public interface BorrowRepository extends MongoRepository<Borrowed, String> {

    List<Borrowed> findByBorrower(String name);

    List<Borrowed> findByBorrowedFrom(Date date);

    List<Borrowed> findByBookIn(List<String> titles);

    List<Borrowed> findByBorrowerAndBorrowedFromAfterAndBorrowedToBefore(String user, Date startDate, Date endDate);

}
