package com.yanhezv.ledgerservice.domain.model;

import com.yanhezv.ledgerservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {
    private final Currency usd = Currency.of("USD");
    private final Currency eur = Currency.of("EUR");

    @Test
    void shouldCreateValidMoney() {
        Money money = Money.of(BigDecimal.valueOf(100), usd);

        assertEquals(BigDecimal.valueOf(100), money.amount());
        assertEquals(usd, money.currency());
        assertEquals("100 USD", money.toString());
    }

    @Test
    void shouldFailWhenAmountIsNegative() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Money.of(BigDecimal.valueOf(-1), usd);
        });

        assertEquals("Amount must be zero or positive", exception.getMessage());
    }

    @Test
    void shouldFailWhenCurrencyIsNull() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Money.of(BigDecimal.TEN, null);
        });

        assertEquals("Currency must be defined", exception.getMessage());
    }

    @Test
    void shouldAddMoneyWithSameCurrency() {
        Money a = Money.of(BigDecimal.valueOf(50), usd);
        Money b = Money.of(BigDecimal.valueOf(30), usd);

        Money result = a.add(b);

        assertEquals(BigDecimal.valueOf(80), result.amount());
        assertEquals(usd, result.currency());
    }

    @Test
    void shouldFailWhenAddingDifferentCurrency() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Money a = Money.of(BigDecimal.valueOf(50), usd);
            Money b = Money.of(BigDecimal.valueOf(50), eur);

            a.add(b);
        });

        assertEquals("Currencies must match", exception.getMessage());
    }

    @Test
    void shouldSubtractMoneyWithSameCurrency() {
        Money a = Money.of(BigDecimal.valueOf(100), usd);
        Money b = Money.of(BigDecimal.valueOf(30), usd);

        Money result = a.subtract(b);

        assertEquals(BigDecimal.valueOf(70), result.amount());
    }

    @Test
    void shouldFailWhenSubtractingDifferentCurrency() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Money a = Money.of(BigDecimal.valueOf(50), usd);
            Money b = Money.of(BigDecimal.valueOf(50), eur);

            a.subtract(b);
        });

        assertEquals("Currencies must match", exception.getMessage());
    }

    @Test
    void shouldFailWhenSubtractingResultNegative() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Money a = Money.of(BigDecimal.valueOf(50), usd);
            Money b = Money.of(BigDecimal.valueOf(60), usd);

            a.subtract(b);
        });

        assertEquals("Resulting amount cannot be negative", exception.getMessage());
    }

    @Test
    void equalityAndHashCodeShouldBeCorrect() {
        Money a = Money.of(BigDecimal.valueOf(10.0), usd);
        Money b = Money.of(BigDecimal.valueOf(10.00), usd);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
