package com.bdran.fintech.controllers;


import com.bdran.fintech.dto.TransactionDepositDTO;
import com.bdran.fintech.dto.TransactionWithdrawDTO;
import com.bdran.fintech.models.Transaction;
import com.bdran.fintech.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionDepositDTO transactionDepositDTO) {
        Transaction transaction = transactionService.deposit(transactionDepositDTO.getAccountId(), transactionDepositDTO.getAmount());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionWithdrawDTO transactionWithdrawDTO) throws Exception {
        Transaction transaction = transactionService.withdraw(transactionWithdrawDTO.getAccountId(), transactionWithdrawDTO.getAmount());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId) {
        BigDecimal balance = transactionService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }
}

