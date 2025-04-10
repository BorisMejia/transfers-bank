package com.backfam.transfers.domain.entity;

import com.backfam.transfers.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.updateBalance(BigDecimal.valueOf(100));
    }
    @Test
    void deposit_shouldIncreaseBalance() {
        account.deposit(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), account.getBalance());
    }

    @Test
    void deposit_shouldThrowExceptionIfAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.valueOf(-10)));
    }

    @Test
    void cashOut_shouldDecreaseBalance() {
        account.cashOut(BigDecimal.valueOf(40));
        assertEquals(BigDecimal.valueOf(60), account.getBalance());
    }

    @Test
    void cashOut_shouldThrowIfInsufficientFunds() {
        assertThrows(InsufficientBalanceException.class, () -> account.cashOut(BigDecimal.valueOf(200)));
    }

    @Test
    void cashOut_shouldThrowIfAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> account.cashOut(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.cashOut(BigDecimal.valueOf(-1)));
    }

}
