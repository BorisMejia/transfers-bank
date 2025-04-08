package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.service.TransactionService;
import com.backfam.transfers.presentation.controller.exception.Messages;
import com.backfam.transfers.presentation.request.TransactionRequestDTO;
import com.backfam.transfers.presentation.response.TransactionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> performTransaction(@RequestBody TransactionRequestDTO request) throws Exception {
        // Llamar al servicio con el DTO de aplicación
        var transactionDTO = transactionService.performTransaction(
                new com.backfam.transfers.application.dto.request.TransactionRequestDTO(
                        request.getAccountNum(),
                        request.getAmount(),
                        request.getType()
                )
        );
        // Convertir la respuesta de aplicación a presentación
        try {
            var response = new TransactionResponseDTO(
                    transactionDTO.getId(),
                    transactionDTO.getAccountNum(),
                    transactionDTO.getAmount(),
                    transactionDTO.getType(),
                    transactionDTO.getMovementDate()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.TRANSACTION_ERROR);
        }
    }
}
