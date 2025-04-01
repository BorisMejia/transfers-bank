package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.TransactionRequestDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import com.backfam.transfers.domain.exception.AccountNoFound;
import com.backfam.transfers.domain.exception.InsufficientBalanceException;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.backfam.transfers.application.dto.TransactionResponseDTO;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EventPublisher eventPublisher;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, EventPublisher eventPublisher){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public TransactionResponseDTO performTransaction(TransactionRequestDTO request){
        Account account = accountRepository.findByAccount(request.getAccountNum())
                .orElseThrow(() -> new AccountNoFound("Account no found"));

        if (request.getType().equalsIgnoreCase("RETIRO")){
            account.cashOut(request.getAmount());
        } else if (request.getType().equalsIgnoreCase("DEPOSITO")) {
            account.deposit(request.getAmount());
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(request.getAmount())
                .type(request.getType().toUpperCase())
                .movementDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);

        TransactionCreateEvent event = new TransactionCreateEvent(
                account.getAccountNum(),
                request.getAmount(),
                request.getType(),
                LocalDateTime.now()
        );
        eventPublisher.publicEvent(event);

        return new TransactionResponseDTO(account.getAccountNum(), account.getBalance(), request.getType());
    }
}
