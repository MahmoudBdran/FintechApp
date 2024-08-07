package com.bdran.fintech.services;
import com.bdran.fintech.models.Account;
import com.bdran.fintech.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account openAccount(String ownerName) {
        Account account = new Account();
        account.setOwnerName(ownerName);
        return accountRepository.save(account);
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
}

