package com.yanhezv.ledgerservice.infrastructure.persistence;


import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Currency;
import com.yanhezv.ledgerservice.domain.model.Money;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import com.yanhezv.ledgerservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTransactionRepositoryTest {

    private final Money money = Money.of(BigDecimal.valueOf(50), Currency.of("USD"));

    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTransactionRepository();
    }

    @Test
    void shouldSaveAndFindTransactionById() {
        Transaction tx = Transaction.create(money, TransactionType.CREDIT, "Test");

        repository.save(tx);

        var result = repository.findById(tx.id());

        assertTrue(result.isPresent());
        assertEquals(tx, result.get());
    }

    @Test
    void shouldReturnEmptyWhenTransactionDoesNotExist() {
        var result = repository.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllTransactions() {
        repository.save(Transaction.create(money, TransactionType.CREDIT, "T1"));
        repository.save(Transaction.create(money, TransactionType.DEBIT, "T2"));

        var all = repository.findAll();

        assertEquals(2, all.size());
    }
}
