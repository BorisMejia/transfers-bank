package com.backfam.transfers.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TransferDTO {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
}
