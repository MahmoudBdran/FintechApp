package com.bdran.fintech;
import com.bdran.fintech.enums.TransactionType;
import com.bdran.fintech.models.Account;
import com.bdran.fintech.models.Transaction;
import com.bdran.fintech.repositories.TransactionRepository;
import com.bdran.fintech.services.AccountService;
import com.bdran.fintech.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.ZERO);

        when(accountService.getAccount(accountId)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.deposit(accountId, new BigDecimal("100.00"));

        assertNotNull(transaction);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(new BigDecimal("100.00"), transaction.getAmount());
        assertEquals(account, transaction.getAccount());
    }

    @Test
    void testWithdraw() throws Exception {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal("200.00"));

        when(accountService.getAccount(accountId)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.withdraw(accountId, new BigDecimal("50.00"));

        assertNotNull(transaction);
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(new BigDecimal("50.00"), transaction.getAmount());
        assertEquals(account, transaction.getAccount());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal("30.00"));

        when(accountService.getAccount(accountId)).thenReturn(account);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdraw(accountId, new BigDecimal("50.00"));
        });

        String expectedMessage = "Insufficient funds";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetBalance() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal("300.00"));

        when(accountService.getAccount(accountId)).thenReturn(account);

        BigDecimal balance = transactionService.getBalance(accountId);

        assertEquals(new BigDecimal("300.00"), balance);
    }
}

