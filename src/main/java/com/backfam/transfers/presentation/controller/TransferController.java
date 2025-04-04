package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.dto.TransferDTO;
import com.backfam.transfers.application.service.TransferService;
import com.backfam.transfers.presentation.request.TransferRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<String> makeTransfer(@RequestBody TransferRequestDTO request) {
        transferService.transfer(new TransferDTO(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        ));
        return ResponseEntity.ok("Transferencia realizada con éxito.");
    }
}
