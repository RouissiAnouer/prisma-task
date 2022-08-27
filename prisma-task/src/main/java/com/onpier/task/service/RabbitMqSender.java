package com.onpier.task.service;

import com.onpier.task.repository.document.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {

        rabbitTemplate.convertAndSend(queueName, message);
    }
}
