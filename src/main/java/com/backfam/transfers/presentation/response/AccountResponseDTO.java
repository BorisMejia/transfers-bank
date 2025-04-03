package com.backfam.transfers.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponseDTO {

    private Integer id;
    private String accountNum;
    private String name;
    private BigDecimal balance;
}
