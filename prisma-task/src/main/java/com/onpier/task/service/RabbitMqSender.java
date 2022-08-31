package com.onpier.task.service;

import com.onpier.task.messaging.MessageType;
import com.onpier.task.repository.document.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
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

    public void send(String message, MessageType type) {
        MessageProperties prop = MessagePropertiesBuilder.newInstance()
                .setType(type.name())
                .build();

        Message m = new Message(message.getBytes(), prop);
        rabbitTemplate.convertAndSend(queueName, m);
    }
}
