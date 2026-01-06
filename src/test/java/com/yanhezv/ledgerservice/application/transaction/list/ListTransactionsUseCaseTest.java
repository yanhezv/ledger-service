package com.yanhezv.ledgerservice.application.transaction.list;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListTransactionsUseCaseTest {

    private TransactionRepository repository;
    private ListTransactionsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(TransactionRepository.class);
        useCase = new ListTransactionsUseCase(repository);
    }

    @Test
    void shouldReturnAllTransactions() {
        Transaction t1 = mock(Transaction.class);
        Transaction t2 = mock(Transaction.class);

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Transaction> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals(List.of(t1, t2), result);
    }

    @Test
    void shouldReturnEmptyListWhenNoTransactionsExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<Transaction> result = useCase.execute();

        assertEquals(0, result.size());
    }
}
