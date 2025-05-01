package com.backfam.transfers.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    private String accountNum;
    private BigDecimal amount;
    private String type;

}
