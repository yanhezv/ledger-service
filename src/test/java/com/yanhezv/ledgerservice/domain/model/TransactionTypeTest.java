package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTypeTest {

    @Test
    void shouldCreateCreditFromString() {
        TransactionType type = TransactionType.from("credit");

        assertEquals(TransactionType.CREDIT, type);
    }

    @Test
    void shouldCreateDebitFromUppercaseString() {
        TransactionType type = TransactionType.from("DEBIT");

        assertEquals(TransactionType.DEBIT, type);
    }

    @Test
    void shouldFailWhenTypeIsInvalid() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            TransactionType.from("foo");
        });

        assertEquals("Invalid transaction type: foo", exception.getMessage());
    }

    @Test
    void shouldFailWhenTypeIsBlank() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            TransactionType.from(" ");
        });

        assertEquals("Transaction type cannot be null or blank", exception.getMessage());
    }
}
