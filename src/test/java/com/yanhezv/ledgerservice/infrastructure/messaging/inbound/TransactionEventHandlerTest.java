package com.yanhezv.ledgerservice.infrastructure.messaging.inbound;

import com.yanhezv.ledgerservice.application.transaction.create.CreateTransactionCommand;
import com.yanhezv.ledgerservice.application.transaction.create.CreateTransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TransactionEventHandlerTest {

    private CreateTransactionUseCase useCase;
    private TransactionEventHandler handler;

    @BeforeEach
    void setUp() {
        useCase = mock(CreateTransactionUseCase.class);
        handler = new TransactionEventHandler(useCase);
    }

    @Test
    void shouldConvertEventToCommandAndExecuteUseCase() {
        BigDecimal amount = new BigDecimal("100.00");
        ExternalTransactionEvent event = new ExternalTransactionEvent("ext-123", amount, "USD", "credit", "Salary", "corr-1");

        handler.handle(event);

        ArgumentCaptor<CreateTransactionCommand> captor = ArgumentCaptor.forClass(CreateTransactionCommand.class);

        verify(useCase).execute(captor.capture());

        CreateTransactionCommand command = captor.getValue();

        assertEquals(amount, command.amount());
        assertEquals("USD", command.currency());
        assertEquals("credit", command.type());
        assertEquals("Salary", command.description());
    }
}
