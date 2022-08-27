package com.onpier.task.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MessageHandler {

    @RabbitListener(queues = "onpier_queue")
    public void receivedMessage(String message) {
        log.info("response from history is ... " + message);
    }

}
