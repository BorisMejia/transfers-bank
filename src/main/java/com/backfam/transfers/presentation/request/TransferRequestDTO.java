package com.backfam.transfers.presentation.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequestDTO {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
}
