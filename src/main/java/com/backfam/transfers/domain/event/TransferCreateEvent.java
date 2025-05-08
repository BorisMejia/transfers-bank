package com.backfam.transfers.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferCreateEvent implements Serializable {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime transferDate;
}
