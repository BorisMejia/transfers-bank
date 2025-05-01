package com.backfam.transfers.application.dto;

import com.backfam.transfers.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private String name;
    private BigDecimal balance;

}
