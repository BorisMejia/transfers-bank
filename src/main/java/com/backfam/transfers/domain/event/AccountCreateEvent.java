package com.backfam.transfers.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountCreateEvent implements Serializable {

    private Integer id;
    private String accountNum;
    private String name;
    private BigDecimal balance;
}
