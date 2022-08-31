package com.prisma.apihistory.config;

import com.prisma.apihistory.model.History;
import com.prisma.apihistory.model.repository.HistoryPrismaHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
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
    public void receivedMessage(Message message) {
        String body = new String(message.getBody());
        log.info("Details Received are.. " + body);
        log.info(message.getMessageProperties().getType());
        MessageType messageType = MessageType.valueOf(message.getMessageProperties().getType());
        switch (messageType) {
            case HIGH_ALERT: log.error("Details Received are.. " + body); break;
            case LOW_ALERT: log.info("Details Received are.. " + body); break;
            case NORMAL_ALERT: log.warn("Details Received are.. " + body); break;
            default: log.info("nothing");
        }
        historyRepository.save(History.builder().message(body).timestamp(new Date().getTime()).build());
        rabbitTemplate.convertAndSend(queueName, "ok fine history saved !!!");
    }

}
