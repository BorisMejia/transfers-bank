package com.backfam.transfers.domain.entity;

import com.backfam.transfers.domain.exception.Messages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;
    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDateTime transferDate;

    public Transfer(Account fromAccount, Account toAccount,BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transferDate = LocalDateTime.now();
    }

    private static void validateAmount(BigDecimal amount){
            if (amount == null || amount.compareTo(BigDecimal.ZERO)<- 0){
                throw new IllegalArgumentException(Messages.TRANSFER_AMOUNT_POSITIVE.getMessage());
            }
        }
}
