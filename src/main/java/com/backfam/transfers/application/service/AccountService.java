package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.event.AccountCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EventPublisher eventPublisher;

    public AccountService(AccountRepository accountRepository, EventPublisher eventPublisher){
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }

    public AccountDTO getBalance(String accountNum){
        Account account = accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new AccountException(accountNum));
        return new AccountDTO(account.getId(),account.getAccountNum(),account.getName(),account.getBalance());
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO){
        Account newAccount = accountRepository.save(accountDTO.toEntity());

        AccountCreateEvent event = new AccountCreateEvent(
                newAccount.getId(),
                newAccount.getAccountNum(),
                newAccount.getName(),
                newAccount.getBalance()
        );
        eventPublisher.publishEvent(event);

        return new AccountDTO(newAccount.getId(), newAccount.getAccountNum(), newAccount.getName(), newAccount.getBalance());

    }

    @Transactional
    public void updateBalance(String accountNum, BigDecimal newBalance){
        Account account = accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new AccountException(accountNum));
        account.updateBalance(newBalance);
        accountRepository.save(account);
    }

}
