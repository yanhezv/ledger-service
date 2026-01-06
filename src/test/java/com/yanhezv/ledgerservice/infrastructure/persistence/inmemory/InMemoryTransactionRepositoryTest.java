package com.yanhezv.ledgerservice.infrastructure.persistence.inmemory;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Currency;
import com.yanhezv.ledgerservice.domain.model.Money;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import com.yanhezv.ledgerservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTransactionRepositoryTest {

    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTransactionRepository();
    }

    @Test
    void shouldSaveAndFindTransactionById() {
        Money amount = Money.of(new BigDecimal("100"), Currency.of("USD"));
        Transaction transaction = Transaction.create(amount, TransactionType.CREDIT, "Salary");

        repository.save(transaction);

        Optional<Transaction> result = repository.findById(transaction.id());

        assertTrue(result.isPresent());
        assertEquals(transaction, result.get());
    }

    @Test
    void shouldReturnEmptyWhenTransactionDoesNotExist() {
        Optional<Transaction> result = repository.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllSavedTransactions() {
        Money tenUsdAmount = Money.of(BigDecimal.TEN, Currency.of("USD"));
        Transaction t1 = Transaction.create(tenUsdAmount, TransactionType.CREDIT, "One");

        Money oneEurAmount = Money.of(BigDecimal.ONE, Currency.of("EUR"));
        Transaction t2 = Transaction.create(oneEurAmount, TransactionType.DEBIT, "Two");

        repository.save(t1);
        repository.save(t2);

        List<Transaction> all = repository.findAll();

        assertEquals(2, all.size());
        assertTrue(all.contains(t1));
        assertTrue(all.contains(t2));
    }
}
