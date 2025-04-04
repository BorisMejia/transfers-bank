package com.backfam.transfers.infrastructure.messaging.RabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class TransferRabbit {

    @Bean
    public Queue transferQueue(){
        return new Queue("q.back.transfer", true);
    }

    @Bean
    public DirectExchange transferExchange(){
        return new DirectExchange("e.back.transfer");
    }

    @Bean
    public Binding binding(Queue transferQueue, DirectExchange transferExchange) {
        return BindingBuilder.bind(transferQueue).to(transferExchange).with("transferRoutingKey");
    }
}
