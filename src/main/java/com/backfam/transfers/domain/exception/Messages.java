package com.backfam.transfers.domain.exception;

import lombok.Getter;

@Getter
public enum Messages {

    DEPOSIT_AMOUNT_POSITIVE("El monto a depositar debe ser positivo."),
    CASH_OUT_AMOUNT_POSITIVE("El monto a retirar debe ser positivo."),
    INSUFFICIENT_BALANCE("Saldo insuficiente en la cuenta.");

    private final String message;

    Messages(String message){
        this.message = message;
    }

    public String format(Object args){
        return String.format(message, args);
    }

}
