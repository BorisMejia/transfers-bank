package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.TransferDTO;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(TransferDTO transferDTO) {
        Account from = accountRepository.findByAccountNum(transferDTO.getFromAccount())
                .orElseThrow(() -> new AccountException(transferDTO.getFromAccount()));
        Account to = accountRepository.findByAccountNum(transferDTO.getToAccount())
                .orElseThrow(() -> new AccountException(transferDTO.getToAccount()));

        from.cashOut(transferDTO.getAmount());
        to.deposit(transferDTO.getAmount());

        accountRepository.save(from);
        accountRepository.save(to);

    }
}
