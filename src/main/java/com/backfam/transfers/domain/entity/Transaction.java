package com.backfam.transfers.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime movementDate;

    private Transaction(Account account, BigDecimal amount, String type){
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.movementDate = LocalDateTime.now();
    }
    public static Transaction createMovement(Account account, BigDecimal amount, String type){
        return new Transaction(account, amount, type);
    }
}
