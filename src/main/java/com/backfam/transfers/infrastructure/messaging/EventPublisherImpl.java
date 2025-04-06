package com.backfam.transfers.infrastructure.messaging;

import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.event.AccountCreateEvent;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import com.backfam.transfers.domain.event.TransferCreateEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public <T> void publishEvent(T event){
        String exchange = "";
        String routingKey = "";

        if (event instanceof AccountCreateEvent){
            exchange = "e.back.account";
            routingKey = "accountRoutingKey";
        } else if (event instanceof TransactionCreateEvent) {
            exchange = "bank.transaction";
            routingKey = "transactionRoutingKey";
        }else if (event instanceof TransferCreateEvent){
            exchange = "e.back.transfer";
            routingKey = "transferRoutingKey";
        }
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
