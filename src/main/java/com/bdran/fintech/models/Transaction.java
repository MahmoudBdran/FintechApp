package com.bdran.fintech.models;

import com.bdran.fintech.enums.TransactionType;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType type; // DEPOSIT or WITHDRAWAL

}

