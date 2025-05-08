package com.backfam.transfers.domain.entity;

import com.backfam.transfers.domain.exception.InsufficientBalanceException;
import com.backfam.transfers.domain.exception.Messages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String accountNum;
    private String name;
    private BigDecimal balance;

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(Messages.DEPOSIT_AMOUNT_POSITIVE.getMessage());
        }
        this.balance = this.balance.add(amount);
    }

    public void cashOut(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(Messages.CASH_OUT_AMOUNT_POSITIVE.getMessage());
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(Messages.INSUFFICIENT_BALANCE.getMessage());
        }
        this.balance = this.balance.subtract(amount);
    }

    public  void updateBalance(BigDecimal newBalance)
    {
        this.balance = newBalance;
    }
}
