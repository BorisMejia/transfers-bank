package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.service.AccountService;
import com.backfam.transfers.presentation.request.AccountRequestDTO;
import com.backfam.transfers.presentation.response.AccountResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/{accountNum}/balance")
    public ResponseEntity<?> getAccount(@PathVariable String accountNum)
    {
        try {
            var account = accountService.getBalance(accountNum);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AccountResponseDTO(account.getId(), account.getAccountNum(), account.getName(), account.getBalance()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.ACCOUNT_ERROR.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Validated AccountDTO request){
        try {
            var accountDTO = accountService.createAccount(request);
            var response = new AccountResponseDTO(accountDTO.getId(), accountDTO.getAccountNum(), accountDTO.getName(), accountDTO.getBalance());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.ACCOUNT_CREATE_ERROR.getMessage());
        }
    }
}
