package com.backfam.transfers.domain.exception;

import lombok.Getter;

@Getter
public enum TransactionType {

    RETIRO("RETIRO"),
    DEPOSITO("DEPOSITO"),
    TRANSACTION_NO_VALID("Transaction no valida");

    private final String message;

    TransactionType(String message){
        this.message = message;
    }

    public String format(Object args){
        return String.format(message, args);
    }
}
