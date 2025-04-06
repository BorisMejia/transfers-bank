package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.request.TransactionRequestDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.backfam.transfers.application.dto.response.TransactionResponseDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public TransactionResponseDTO performTransaction(TransactionRequestDTO request){
        Account account = accountRepository.findByAccountNum(request.getAccountNum())
                .orElseThrow(() -> new AccountException(request.getAccountNum()));

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
                transaction.getMovementDate()
        );
        eventPublisher.publishEvent(event);

        return new TransactionResponseDTO(
                transaction.getId(),
                account.getAccountNum(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getMovementDate()
        );
    }
}
