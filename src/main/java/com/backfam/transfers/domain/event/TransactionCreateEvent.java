package com.backfam.transfers.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateEvent implements Serializable {

    private String accountNum;
    private BigDecimal amount;
    private String type;
    private LocalDateTime movementDate;
}
