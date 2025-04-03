package com.backfam.transfers.presentation.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    private String accountNum;
    private BigDecimal amount;
    private String type;
}
