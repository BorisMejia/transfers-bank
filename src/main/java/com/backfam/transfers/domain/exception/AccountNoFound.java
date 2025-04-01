package com.backfam.transfers.domain.exception;

public class AccountNoFound extends RuntimeException{
    public AccountNoFound(String message){
        super(message);
    }
}
