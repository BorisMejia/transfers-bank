package com.backfam.transfers.application.dto.response;

import lombok.Data;


@Data
public class AccountResponseDTO {

    private String accountNum;
    private String name;

    private Double balance;
}
