package com.backfam.transfers.application.event;

import com.backfam.transfers.domain.event.AccountCreateEvent;
import com.backfam.transfers.domain.event.TransactionCreateEvent;

public interface EventPublisher {

    <T> void publishEvent(T event);
}
