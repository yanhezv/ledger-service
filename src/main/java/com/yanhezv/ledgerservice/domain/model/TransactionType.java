package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;

public enum TransactionType {
    CREDIT,
    DEBIT;

    public static TransactionType from(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new InvalidTransactionTypeException("Transaction type cannot be null or blank");
        }

        try {
            return TransactionType.valueOf(raw.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidTransactionTypeException("Invalid transaction type: " + raw);
        }
    }

    private static final class InvalidTransactionTypeException extends DomainException {
        InvalidTransactionTypeException(String message) {
            super(message);
        }
    }
}
