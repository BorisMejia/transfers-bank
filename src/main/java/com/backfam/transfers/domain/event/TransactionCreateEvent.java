package com.backfam.transfers.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateEvent {

    private String accountNum;
    private BigDecimal amount;
    private String type;
    private LocalDateTime movementDate;
}
