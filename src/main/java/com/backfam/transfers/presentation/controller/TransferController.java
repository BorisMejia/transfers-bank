package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.dto.TransferDTO;
import com.backfam.transfers.application.service.TransferService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> transfer(@RequestBody TransferDTO request) {
        try{
            transferService.transfer(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Messages.TRANSFER_SUCCESS.getMessage());
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.TRANSFER_ERROR.getMessage());
        }
    }
}
