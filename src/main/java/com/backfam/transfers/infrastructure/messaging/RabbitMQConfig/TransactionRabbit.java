package com.backfam.transfers.infrastructure.messaging.RabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class TransactionRabbit {

    @Bean
    public Queue transactionQueue(){
        return new Queue("q.back.transaction", true);
    }

    @Bean
    public DirectExchange transactionExchange(){
        return new DirectExchange("e.bank.transaction");
    }

    @Bean
    public Binding binding(Queue transactionQueue, DirectExchange transactionExchange){
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with("transactionRoutingKey");
    }
}
