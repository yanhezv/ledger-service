package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Currency {
    private static final Pattern ISO_4217_PATTERN = Pattern.compile("^[A-Z]{3}$");

    private final String code;

    private Currency(String code) {
        this.code = code;
    }

    public static Currency of(String code) {
        if (code == null) {
            throw new InvalidCurrencyException("Currency code cannot be null");
        }

        String normalized = code.trim().toUpperCase();

        if (!ISO_4217_PATTERN.matcher(normalized).matches()) {
            throw new InvalidCurrencyException("Invalid currency code: " + code);
        }

        return new Currency(normalized);
    }

    public String code() {
        return code;
    }

    public boolean is(String code) {
        return this.code.equals(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Currency currency)) return false;

        return code.equals(currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code;
    }

    private static final class InvalidCurrencyException extends DomainException {

        InvalidCurrencyException(String message) {
            super(message);
        }
    }
}
