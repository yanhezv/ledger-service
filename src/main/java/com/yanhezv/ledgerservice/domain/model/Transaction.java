package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Transaction {

    private final UUID id;
    private final Money amount;
    private final TransactionType type;
    private final Instant occurredAt;
    private final String description;

    private Transaction(UUID id, Money amount, TransactionType type, Instant occurredAt, String description) {
        if (id == null) throw new InvalidTransactionException("Transaction ID cannot be null");
        if (amount == null) throw new InvalidTransactionException("Transaction amount cannot be null");
        if (type == null) throw new InvalidTransactionException("Transaction type cannot be null");
        if (occurredAt == null) throw new InvalidTransactionException("Transaction date cannot be null");

        this.id = id;
        this.amount = amount;
        this.type = type;
        this.occurredAt = occurredAt;
        this.description = description;
    }

    public static Transaction create(Money amount, TransactionType type, String description) {
        return of(UUID.randomUUID(), amount, type, Instant.now(), description);
    }

    public static Transaction of(UUID id, Money amount, TransactionType type, Instant occurredAt, String description) {
        return new Transaction(id, amount, type, occurredAt, description);
    }

    public UUID id() {
        return id;
    }

    public Money amount() {
        return amount;
    }

    public TransactionType type() {
        return type;
    }

    public Instant occurredAt() {
        return occurredAt;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Transaction transaction)) return false;
        return id.equals(transaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                ", occurredAt=" + occurredAt +
                ", description='" + description + "'" +
                '}';
    }

    private static final class InvalidTransactionException extends DomainException {

        InvalidTransactionException(String message) {
            super(message);
        }
    }
}
