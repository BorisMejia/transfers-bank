package com.backfam.transfers.application.service;

import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.exception.AccountNoFound;
import com.backfam.transfers.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account AccountBalance(String accountNum){
        return accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new AccountNoFound("Acount not found: " + accountNum));
    }

    @Transactional
    public void updateBalance(String accountNum, BigDecimal newBalance){
        Account account = AccountBalance(accountNum);
        account.updateBalance(newBalance);
        accountRepository.save(account);
    }

}
