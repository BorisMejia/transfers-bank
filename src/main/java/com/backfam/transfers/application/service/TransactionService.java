package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.request.TransactionRequestDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.application.exception.Messages;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.exception.TransactionType;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.backfam.transfers.application.dto.response.TransactionResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public TransactionResponseDTO performTransaction(TransactionRequestDTO request) throws Exception {
        try {

            Account account = accountRepository.findByAccountNum(request.getAccountNum())
                    .orElseThrow(() -> new AccountException(request.getAccountNum()));

            String type = request.getType().toUpperCase();

            if (!type.equals(TransactionType.RETIRO.getMessage()) && !type.equals(TransactionType.DEPOSITO.getMessage())){
                throw new IllegalArgumentException(TransactionType.TRANSACTION_NO_VALID.getMessage());
            }

            if (type.equals(TransactionType.RETIRO.getMessage())) {
                account.cashOut(request.getAmount());
            } else{
                account.deposit(request.getAmount());
            }

            Transaction transaction = Transaction.builder()
                    .account(account)
                    .amount(request.getAmount())
                    .type(type)
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

        } catch (Exception e) {
            throw new Exception(Messages.PERFORM_TRANSACTION_ERROR.getMessage());
        }
    }
}
