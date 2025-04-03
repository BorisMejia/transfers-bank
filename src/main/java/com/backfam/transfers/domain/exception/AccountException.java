package com.backfam.transfers.domain.exception;

public class AccountNoFound extends RuntimeException{

    private static final String MESSAGE = "Account not found %s";

    public AccountNoFound(String accountNum){
        super(String.format(MESSAGE,accountNum));
    }
}
