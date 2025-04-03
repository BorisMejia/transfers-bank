package com.backfam.transfers.presentation.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {

    private String accountNum;

    private String name;

    private BigDecimal balance;
}
