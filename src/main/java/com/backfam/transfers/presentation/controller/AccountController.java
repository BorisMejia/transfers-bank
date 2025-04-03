package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.service.AccountService;
import com.backfam.transfers.presentation.request.AccountRequestDTO;
import com.backfam.transfers.presentation.response.AccountResponseDTO;
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

    @GetMapping
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable String accountNum){
        var account = accountService.getBalance(accountNum);
        return ResponseEntity.ok(new AccountResponseDTO(account.getId(), account.getAccountNum(), account.getName(), account.getBalance()));
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Validated AccountDTO request){
        var accountDTO = accountService.createAccount(request);
        var response = new AccountResponseDTO(accountDTO.getId(), accountDTO.getAccountNum(),accountDTO.getName(), accountDTO.getBalance());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{accountNum}/balance")
    public ResponseEntity<Void>updateBalance(@PathVariable String accountNum, @RequestBody BigDecimal newBalance){
        accountService.updateBalance(accountNum, newBalance);
        return ResponseEntity.ok().build();
    }
}
