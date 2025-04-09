package com.backfam.transfers.application.exception;

import lombok.Getter;

@Getter
public enum Messages {

    ERROR_CREATE_ACCOUNT("Error al crear cuenta"),
    ERROR_GET_ACCOUNT("Error al obtener cuenta"),
    INSUFFICIENT_BALANCE("Saldo insuficiente en la cuenta."),
    PERFORM_TRANSACTION_ERROR("Error al realizar la transaccion");


    private final String message;
    Messages(String message){
        this.message = message;
    }
}
