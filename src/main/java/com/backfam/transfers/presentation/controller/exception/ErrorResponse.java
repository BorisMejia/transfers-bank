package com.backfam.transfers.presentation.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private String message;
    public ErrorResponse(String message) {
        this.message = message;
    }
}
