package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {

    private final Currency usd = Currency.of("USD");
    private final Money money = Money.of(BigDecimal.valueOf(100), usd);

    @Test
    void shouldCreateTransactionWithCreate() {
        Transaction tx = Transaction.create(money, TransactionType.CREDIT, "Payment received");

        assertNotNull(tx.id());
        assertNotNull(tx.occurredAt());
        assertEquals(money, tx.amount());
        assertEquals(TransactionType.CREDIT, tx.type());
        assertEquals("Payment received", tx.description());
    }

    @Test
    void shouldReconstructTransactionWithOf() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        Transaction tx = Transaction.of(id, money, TransactionType.DEBIT, now, "Withdrawal");

        assertEquals(id, tx.id());
        assertEquals(now, tx.occurredAt());
        assertEquals(TransactionType.DEBIT, tx.type());
        assertEquals("Withdrawal", tx.description());
    }

    @Test
    void shouldFailWhenIdIsNull() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Transaction.of(null, money, TransactionType.CREDIT, Instant.now(), "desc");
        });

        assertEquals("Transaction ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldFailWhenAmountIsNull() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Transaction.of(UUID.randomUUID(), null, TransactionType.CREDIT, Instant.now(), "desc");
        });

        assertEquals("Transaction amount cannot be null", exception.getMessage());
    }

    @Test
    void shouldFailWhenTypeIsNull() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Transaction.of(UUID.randomUUID(), money, null, Instant.now(), "desc");
        });

        assertEquals("Transaction type cannot be null", exception.getMessage());
    }

    @Test
    void shouldFailWhenOccurredAtIsNull() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Transaction.of(UUID.randomUUID(), money, TransactionType.CREDIT, null, "desc");
        });

        assertEquals("Transaction date cannot be null", exception.getMessage());
    }

    @Test
    void shouldBeEqualWhenIdsMatch() {
        UUID id = UUID.randomUUID();
        Transaction tx1 = Transaction.of(id, money, TransactionType.CREDIT, Instant.now(), "desc1");
        Transaction tx2 = Transaction.of(id, money, TransactionType.DEBIT, Instant.now(), "desc2");

        assertEquals(tx1, tx2);
        assertEquals(tx1.hashCode(), tx2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIdsDiffer() {
        Transaction tx1 = Transaction.create(money, TransactionType.CREDIT, "desc1");
        Transaction tx2 = Transaction.create(money, TransactionType.CREDIT, "desc2");

        assertNotEquals(tx1, tx2);
    }

    @Test
    void shouldContainRelevantInfoInToString() {
        Transaction tx = Transaction.create(money, TransactionType.CREDIT, "desc");
        String str = tx.toString();

        assertTrue(str.contains(tx.id().toString()));
        assertTrue(str.contains("CREDIT"));
        assertTrue(str.contains("100"));
        assertTrue(str.contains("USD"));
        assertTrue(str.contains("desc"));
    }

}
