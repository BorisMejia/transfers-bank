package com.backfam.transfers.infrastructure.messaging;

import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicEvent(TransactionCreateEvent event){
        rabbitTemplate.convertAndSend("bank.transaction", "transactionRoutingKey", event);
    }
}
