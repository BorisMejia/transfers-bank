package com.backfam.transfers.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponseDTO {

    private Integer id;
    private String accountNum;
    private BigDecimal amount;
    private String type;
    private LocalDateTime movementDate;
}
