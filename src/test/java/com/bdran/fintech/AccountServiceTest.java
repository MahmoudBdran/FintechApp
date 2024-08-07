package com.bdran.fintech;
import com.bdran.fintech.models.Account;
import com.bdran.fintech.repositories.AccountRepository;
import com.bdran.fintech.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setOwnerName("John Doe");

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.openAccount("John Doe");

        assertNotNull(result);
        assertEquals("John Doe", result.getOwnerName());
        assertNotNull(result.getId());
    }

    @Test
    void testGetAccount() {
        Long accountId =1L;
        Account account = new Account();
        account.setId(accountId);
        account.setOwnerName("John Doe");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.getAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
    }
}

