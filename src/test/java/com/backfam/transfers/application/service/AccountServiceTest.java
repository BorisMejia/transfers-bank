package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.event.AccountCreateEvent;
import com.backfam.transfers.domain.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
        AccountDTO input = new AccountDTO(null, null, "Carlos", new BigDecimal("1500"));
        Account savedAccount = new Account(1, "1234567890", "Carlos", new BigDecimal("1500"));

        // Simular que el número de cuenta generado no existe aún
        Mockito.when(accountRepository.findByAccountNum(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(savedAccount);

        AccountDTO result = accountService.createAccount(input);

        assertNotNull(result);
        assertEquals("Carlos", result.getName());
        assertEquals("1234567890", result.getAccountNum());
        assertEquals(new BigDecimal("1500"), result.getBalance());
        verify(eventPublisher).publishEvent(Mockito.any(AccountCreateEvent.class));
    }

}
