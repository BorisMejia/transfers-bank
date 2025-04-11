package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.TransferDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.entity.Transaction;
import com.backfam.transfers.domain.entity.Transfer;
import com.backfam.transfers.domain.event.TransferCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.domain.exception.InsufficientBalanceException;
import com.backfam.transfers.domain.repository.AccountRepository;
import com.backfam.transfers.domain.repository.TransactionRepository;
import com.backfam.transfers.domain.repository.TransferRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private TransferService transferService;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        fromAccount = new Account();
        fromAccount.setAccountNum("1111111111");
        fromAccount.setName("Boris");
        fromAccount.setBalance(new BigDecimal("1000.00"));

        toAccount = new Account();
        toAccount.setAccountNum("2222222222");
        toAccount.setName("Alejandro");
        toAccount.setBalance(new BigDecimal("500.00"));
    }

    @Test
    void testTransfer_Success() {
        TransferDTO dto = new TransferDTO("1111111111", "2222222222", new BigDecimal("200.00"));

        when(accountRepository.findByAccountNum("1111111111")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNum("2222222222")).thenReturn(Optional.of(toAccount));

        transferService.transfer(dto);

        assertEquals(new BigDecimal("800.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("700.00"), toAccount.getBalance());

        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(transferRepository).save(any(Transfer.class));
        verify(eventPublisher).publishEvent(any(TransferCreateEvent.class));
    }

    @Test
    void testTransfer_AccountFromNotFound_ThrowsException() {
        TransferDTO dto = new TransferDTO("1111111111", "2222222222", new BigDecimal("100.00"));

        when(accountRepository.findByAccountNum("1111111111")).thenReturn(Optional.empty());

        assertThrows(AccountException.class, () -> transferService.transfer(dto));

        verify(accountRepository, never()).findByAccountNum("2222222222");
        verifyNoInteractions(transactionRepository, transferRepository, eventPublisher);
    }

    @Test
    void testTransfer_InsufficientBalance_ThrowsException() {
        fromAccount.setBalance(new BigDecimal("50.00"));
        TransferDTO dto = new TransferDTO("1111111111", "2222222222", new BigDecimal("200.00"));

        when(accountRepository.findByAccountNum("1111111111")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNum("2222222222")).thenReturn(Optional.of(toAccount));

        assertThrows(InsufficientBalanceException.class, () -> transferService.transfer(dto));

        verify(transactionRepository, never()).save(any());
        verify(transferRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }


}
