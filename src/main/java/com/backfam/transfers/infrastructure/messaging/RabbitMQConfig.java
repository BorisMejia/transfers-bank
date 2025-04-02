package com.backfam.transfers.infrastructure.messaging;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    @Bean
    public Queue transactionQueue(){
        return new Queue("q.back.transfer", true);
    }

    @Bean
    public DirectExchange transactionExchange(){
        return new DirectExchange("bank.transaction");
    }

    @Bean
    public Binding binding(Queue transactionQueue, DirectExchange transactionExchange){
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with("transactionRoutingKey");
    }

}
