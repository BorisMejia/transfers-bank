package com.backfam.transfers.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {

    private Integer id;
    private String accountNum;
    private BigDecimal amount;
    private String type;
    private LocalDateTime movementDate;
}
