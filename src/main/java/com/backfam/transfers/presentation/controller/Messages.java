package com.backfam.transfers.presentation.controller;

import lombok.Getter;

@Getter
public enum Menssages {

    TRANSFER_SUCCESS("Transaction successful"),
    TRANSFER_ERROR("Transaction error"),

    TRANSACTION_ERROR("Transaction error");

    private final String message;


    Menssages(String message){
        this.message = message;
    }

    public String format(Object args){
        return String.format(message, args);
    }
}
public class ErrorResponse{
    
}
