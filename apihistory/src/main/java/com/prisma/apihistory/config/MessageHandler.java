package com.prisma.apihistory.config;

import com.prisma.apihistory.model.History;
import com.prisma.apihistory.model.repository.HistoryPrismaHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MessageHandler {

    @Autowired
    HistoryPrismaHistory historyRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.onpierqueue}")
    private String queueName;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String message) {
        log.info("Details Received are.. " + message);
        historyRepository.save(History.builder().message(message).timestamp(new Date().getTime()).build());
        rabbitTemplate.convertAndSend(queueName, "ok fine history saved !!!");
    }

}
