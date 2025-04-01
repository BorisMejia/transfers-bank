package com.backfam.transfers.application.event;

import com.backfam.transfers.domain.event.TransactionCreateEvent;

public interface EventPublisher {
    void publicEvent(TransactionCreateEvent event);
}
