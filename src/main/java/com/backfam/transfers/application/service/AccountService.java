package com.backfam.transfers.application.service;

import com.backfam.transfers.application.dto.AccountDTO;
import com.backfam.transfers.application.event.EventPublisher;
import com.backfam.transfers.domain.entity.Account;
import com.backfam.transfers.domain.event.AccountCreateEvent;
import com.backfam.transfers.domain.exception.AccountException;
import com.backfam.transfers.application.exception.Messages;
import com.backfam.transfers.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EventPublisher eventPublisher;

    private String generateUniqueAccountNumber() {
        String accountNum;
        do {
            accountNum = String.format("%010d", new Random().nextInt(1_000_000_000));
        } while (accountRepository.findByAccountNum(accountNum).isPresent());
        return accountNum;
    }

    public AccountService(AccountRepository accountRepository, EventPublisher eventPublisher){
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }

    public AccountDTO getBalance(String accountNum)throws Exception{
        try {
            Account account = accountRepository.findByAccountNum(accountNum)
                    .orElseThrow(()-> new AccountException(accountNum));
            return new AccountDTO(account.getId(),account.getAccountNum(),account.getName(),account.getBalance());
        }catch (Exception e){
            throw new Exception(Messages.ERROR_GET_ACCOUNT.getMessage());
        }
    }
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) throws Exception{
        try {
            String generatedAccountNum = generateUniqueAccountNumber();

            Account newAccount = new Account(
                    null,
                    generatedAccountNum,
                    accountDTO.getName(),
                    accountDTO.getBalance()
            );

            newAccount = accountRepository.save(newAccount);

            AccountCreateEvent event = new AccountCreateEvent(
                    newAccount.getId(),
                    newAccount.getAccountNum(),
                    newAccount.getName(),
                    newAccount.getBalance()
            );
            eventPublisher.publishEvent(event);

            return new AccountDTO(
                    newAccount.getId(),
                    newAccount.getAccountNum(),
                    newAccount.getName(),
                    newAccount.getBalance()
            );
        }catch (Exception e){
            throw new Exception(Messages.ERROR_CREATE_ACCOUNT.getMessage());
        }
    }
    public List<AccountDTO> getAllAccount() throws Exception{
        List<Account> accounts = accountRepository.findAll();

        System.out.println("Cantidad de cuentas encontradas: " + accounts.size());

        if (accounts.isEmpty()) {
            throw new Exception("No hay cuentas registradas");
        }

        return accounts.stream()
                .map(account -> new AccountDTO(
                        account.getId(),
                        account.getAccountNum(),
                        account.getName(),
                        account.getBalance()
                )).collect(Collectors.toList());
    }

    @Transactional
    public void updateBalance(String accountNum, BigDecimal newBalance){
        Account account = accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new AccountException(accountNum));
        account.updateBalance(newBalance);
        accountRepository.save(account);
    }

}
