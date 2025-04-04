package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.TransferDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.entity.Transfer;
import com.backfam.transfers.domain.event.TransferCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import com.backfam.transfers.domain.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransferRepository transferRepository;
    private final EventPublisher eventPublisher;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository, TransferRepository transferRepository, EventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transferRepository = transferRepository;
        this.eventPublisher = eventPublisher;
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

        transactionRepository.save(Transaction.createMovement(from, transferDTO.getAmount().negate(), "TRANSFER_OUT"));
        transactionRepository.save(Transaction.createMovement(to, transferDTO.getAmount(),"TRANSFER_IN"));

        transferRepository.save(new Transfer(from, to, transferDTO.getAmount()));

        eventPublisher.publishEvent(new TransferCreateEvent(from.getAccountNum(), to.getAccountNum(), transferDTO.getAmount(), LocalDateTime.now()));
    }
}
