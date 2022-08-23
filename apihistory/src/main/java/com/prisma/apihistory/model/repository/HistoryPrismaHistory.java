package com.prisma.apihistory.model.repository;

import com.prisma.apihistory.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryPrismaHistory extends MongoRepository<History, String> {
}
