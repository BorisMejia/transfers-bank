package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.request.TransactionRequestDTO;
import com.backfam.transfers.application.dto.response.TransactionResponseDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.event.TransactionCreateEvent;
import com.backfam.transfers.domain.exception.InsufficientBalanceException;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountNum("1234567890");
        account.setBalance(new BigDecimal("1000.00"));
        account.setName("Juan Perez");
    }

    //Test Deposito correcto
    @Test
    void testPerformTransaction_Deposit_Success() {
        TransactionRequestDTO request = new TransactionRequestDTO("1234567890", new BigDecimal("500.00"), "DEPOSITO");

        when(accountRepository.findByAccountNum("1234567890")).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        TransactionResponseDTO response = transactionService.performTransaction(request);

        assertEquals(new BigDecimal("1500.00"), account.getBalance());
        assertEquals("DEPOSITO", response.getType());
        verify(eventPublisher, times(1)).publishEvent(any(TransactionCreateEvent.class));
    }
    //Test Retiro correcto
    @Test
    void testPerformTransaction_CashOut_Success() {
        TransactionRequestDTO request = new TransactionRequestDTO("1234567890", new BigDecimal("200.00"), "RETIRO");

        when(accountRepository.findByAccountNum("1234567890")).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        TransactionResponseDTO response = transactionService.performTransaction(request);

        assertEquals(new BigDecimal("800.00"), account.getBalance());
        assertEquals("RETIRO", response.getType());
        verify(eventPublisher).publishEvent(any(TransactionCreateEvent.class));
    }
    //Test Type correcto
    @Test
    void testPerformTransaction_InvalidType_ThrowsException() {
        TransactionRequestDTO request = new TransactionRequestDTO("1234567890", new BigDecimal("100.00"), "TRANSFER");

        when(accountRepository.findByAccountNum("1234567890")).thenReturn(Optional.of(account));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.performTransaction(request);
        });

        assertEquals("Transaction no valid", exception.getMessage());
    }
    //Test de saldo insuficiente
    @Test
    void testPerformTransaction_InsufficientBalance_ThrowsException() {
        TransactionRequestDTO request = new TransactionRequestDTO("1234567890", new BigDecimal("2000.00"), "RETIRO");

        when(accountRepository.findByAccountNum("1234567890")).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.performTransaction(request);
        });

        verify(transactionRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
