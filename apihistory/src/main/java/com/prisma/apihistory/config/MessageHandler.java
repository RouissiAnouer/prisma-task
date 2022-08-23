package com.prisma.apihistory.config;

import com.prisma.apihistory.model.History;
import com.prisma.apihistory.model.repository.HistoryPrismaHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MessageHandler {

    @Autowired
    HistoryPrismaHistory historyRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String message) {
        log.info("User Details Received is.. " + message);
        historyRepository.save(History.builder().message(message).timestamp(new Date().getTime()).build());
    }

}
