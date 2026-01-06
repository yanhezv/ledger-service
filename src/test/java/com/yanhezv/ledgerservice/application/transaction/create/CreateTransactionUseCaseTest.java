package com.yanhezv.ledgerservice.application.transaction.create;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.exception.DomainException;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CreateTransactionUseCaseTest {

    private TransactionRepository repository;
    private CreateTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(TransactionRepository.class);
        useCase = new CreateTransactionUseCase(repository);
    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        BigDecimal amount = new BigDecimal("100.00");
        CreateTransactionCommand command = new CreateTransactionCommand(amount, "USD", "credit", "Salary");

        when(repository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = useCase.execute(command);

        assertNotNull(result);
        assertEquals("Salary", result.description());
        assertEquals("USD", result.amount().currency().code());
        assertEquals(amount, result.amount().amount());
        assertEquals("CREDIT", result.type().name());

        verify(repository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldPassCorrectTransactionToRepository() {
        BigDecimal amount = new BigDecimal("50");
        CreateTransactionCommand command = new CreateTransactionCommand(amount, "EUR", "debit", "Groceries");

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        when(repository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(command);

        verify(repository).save(captor.capture());

        Transaction saved = captor.getValue();

        assertEquals("Groceries", saved.description());
        assertEquals("EUR", saved.amount().currency().code());
        assertEquals(amount, saved.amount().amount());
        assertEquals("DEBIT", saved.type().name());
        assertNotNull(saved.id());
        assertNotNull(saved.occurredAt());
    }

    @Test
    void shouldFailWhenTransactionTypeIsInvalid() {
        BigDecimal amount = new BigDecimal("10");
        CreateTransactionCommand command = new CreateTransactionCommand(amount, "USD", "invalid_type", "Test");

        DomainException exception = assertThrows(DomainException.class, () -> {
            useCase.execute(command);
        });

        assertEquals("Invalid transaction type: invalid_type", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldFailWhenCommandIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            useCase.execute(null);
        });

        assertEquals("Command cannot be null", exception.getMessage());

        verifyNoInteractions(repository);
    }
}
