package com.backfam.transfers.domain.exception;


public class AccountException extends RuntimeException{

    private static final String MESSAGE = "Account not found %s";

    public AccountException(String accountNum){
        super(String.format(MESSAGE,accountNum));
    }



}
