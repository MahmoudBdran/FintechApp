package com.bdran.fintech.services;


import com.bdran.fintech.enums.TransactionType;
import com.bdran.fintech.models.Account;
import com.bdran.fintech.models.Transaction;
import com.bdran.fintech.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount) {
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Invalid account ID");
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);

        account.setBalance(account.getBalance().add(amount));
        accountService.openAccount(account.getOwnerName());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount) throws Exception {
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            throw new Exception("Invalid account ID");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAWAL);

        account.setBalance(account.getBalance().subtract(amount));
        accountService.openAccount(account.getOwnerName());

        return transactionRepository.save(transaction);
    }

    public BigDecimal getBalance(Long accountId) {
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return account.getBalance();
    }
}

