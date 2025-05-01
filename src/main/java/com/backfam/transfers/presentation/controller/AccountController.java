package com.backfam.transfers.presentation.controller;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.service.AccountService;
import com.backfam.transfers.presentation.controller.exception.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                    .body(new AccountDTO(account.getName(), account.getBalance()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.ACCOUNT_ERROR.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() throws Exception{
        try {
            var accounts = accountService.getAllAccount();
            List<AccountDTO> response = accounts.stream()
                    .map(account -> new AccountDTO(
                            account.getName(),
                            account.getBalance()
                    )).collect(Collectors.toList());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            System.out.println("Error al obtener cuentas" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Collections.emptyList());
        }

    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Validated AccountDTO request){
        try {
            var accountDTO = accountService.createAccount(request);
            var response = new AccountDTO(accountDTO.getName(), accountDTO.getBalance());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cuenta creada correctamente");
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Messages.ACCOUNT_CREATE_ERROR.getMessage());
        }
    }
}
