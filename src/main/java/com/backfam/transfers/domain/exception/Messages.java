package com.backfam.transfers.domain.exception;

import lombok.Getter;

@Getter
public enum Messages {

    DEPOSIT_AMOUNT_POSITIVE("El monto a depositar debe ser positivo."),
    CASH_OUT_AMOUNT_POSITIVE("El monto a retirar debe ser positivo."),
    INSUFFICIENT_BALANCE("Saldo insuficiente en la cuenta."),
    TRANSFER_AMOUNT_POSITIVE("El monto a transferir debe ser positivo"),
    TRANSFER_ACCOUNT_ERROR("Transferencia no valida");

    private final String message;

    Messages(String message){
        this.message = message;
    }

    public String format(Object args){
        return String.format(message, args);
    }

}
