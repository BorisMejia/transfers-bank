package com.backfam.transfers.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private Integer id;
    private String accountNum;
    private BigDecimal amount;
    private String type;
    private LocalDateTime movementDate;
}
