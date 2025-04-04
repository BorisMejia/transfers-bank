package com.backfam.transfers.domain.entity;

import com.backfam.transfers.domain.exception.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Transfer {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime transferDate;

    public Transfer(String fromAccount, String toAccount,BigDecimal amount) {
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
