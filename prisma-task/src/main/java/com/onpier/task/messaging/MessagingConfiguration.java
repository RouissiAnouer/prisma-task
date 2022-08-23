package com.onpier.task.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableRabbit
public class MessagingConfiguration {

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public List<Binding> binding() {
        return Arrays.asList(BindingBuilder.bind(queue()).to(exchange()).with(routingkey));
    }


}