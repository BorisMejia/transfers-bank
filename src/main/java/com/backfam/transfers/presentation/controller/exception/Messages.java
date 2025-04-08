package com.backfam.transfers.presentation.controller.exception;

import lombok.Getter;

@Getter
public enum Messages {

    TRANSFER_SUCCESS("Transaction successful"),
    TRANSFER_ERROR("Transaction error"),

    ACCOUNT_ERROR("Error in the account"),
    ACCOUNT_CREATE_ERROR("Account creation error"),

    TRANSACTION_ERROR("Transaction error");

    private final String message;


    Messages(String message){
        this.message = message;
    }

    public String format(Object args){
        return String.format(message, args);
    }
}
