package com.backfam.transfers.infrastructure.messaging.RabbitMQConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

public class AccountRabbit {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue accountQueue(){return new Queue("q.back.accountCreate", true);}

    @Bean
    public Exchange accountExchenge(){return new DirectExchange("e.back.account");
    }

    @Bean
    public Binding binding(Queue accountQueue, DirectExchange accountExchenge){
        return BindingBuilder.bind(accountQueue).to(accountExchenge).with("accountRoutingKey");
    }
}
