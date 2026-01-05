package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyTest {
    @Test
    void shouldCreateValidCurrency() {
        Currency usd = Currency.of("USD");
        assertEquals("USD", usd.getCode());
        assertEquals("USD", usd.toString());
    }

    @Test
    void shouldNormalizeCurrency() {
        Currency usd = Currency.of(" usd ");
        assertEquals("USD", usd.getCode());
    }

    @Test
    void shouldFailWithInvalidCode() {
        DomainException firstException = assertThrows(DomainException.class, () -> Currency.of("US"));
        assertEquals("Invalid currency code: US", firstException.getMessage());

        DomainException secondException = assertThrows(DomainException.class, () -> Currency.of("USDD"));
        assertEquals("Invalid currency code: USDD", secondException.getMessage());

        DomainException thirdException = assertThrows(DomainException.class, () -> Currency.of(""));
        assertEquals("Invalid currency code: ", thirdException.getMessage());
    }

    @Test
    void shouldFailWithNull() {
        DomainException exception = assertThrows(DomainException.class, () -> Currency.of(null));
        assertEquals("Currency code cannot be null", exception.getMessage());
    }

    @Test
    void equalityShouldBeBasedOnCode() {
        Currency usd1 = Currency.of("USD");
        Currency usd2 = Currency.of("usd");
        assertEquals(usd1, usd2);
        assertEquals(usd1.hashCode(), usd2.hashCode());
    }
}
