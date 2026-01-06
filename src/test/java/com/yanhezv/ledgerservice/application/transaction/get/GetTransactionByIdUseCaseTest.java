package com.yanhezv.ledgerservice.application.transaction.get;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Currency;
import com.yanhezv.ledgerservice.domain.model.Money;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import com.yanhezv.ledgerservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class GetTransactionByIdUseCaseTest {

    private TransactionRepository repository;
    private GetTransactionByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(TransactionRepository.class);
        useCase = new GetTransactionByIdUseCase(repository);
    }

    @Test
    void shouldReturnTransactionWhenFound() {
        UUID id = UUID.randomUUID();

        Money amount = Money.of(new BigDecimal("100"), Currency.of("USD"));
        Transaction transaction = Transaction.create(amount, TransactionType.CREDIT, "Salary");

        when(repository.findById(id)).thenReturn(Optional.of(transaction));

        Transaction result = useCase.execute(id);

        assertNotNull(result);
        assertEquals(transaction, result);
        verify(repository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            useCase.execute(id);
        });

        assertEquals("Transaction not found: " + id, exception.getMessage());
        verify(repository).findById(id);
    }

    @Test
    void shouldFailWhenTransactionIdIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            useCase.execute(null);
        });

        assertEquals("TransactionId cannot be null", exception.getMessage());
        verifyNoInteractions(repository);
    }
}
