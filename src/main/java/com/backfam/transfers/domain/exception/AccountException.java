package com.backfam.transfers.domain.exception;

import java.math.BigDecimal;

public class AccountException extends RuntimeException{

    private static final String MESSAGE = "Account not found %s";

    public AccountException(String accountNum){
        super(String.format(MESSAGE,accountNum));
    }



}
