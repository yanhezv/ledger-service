package com.yanhezv.ledgerservice.infrastructure.messaging.inbound;

import com.yanhezv.ledgerservice.application.transaction.create.CreateTransactionCommand;
import com.yanhezv.ledgerservice.application.transaction.create.CreateTransactionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TransactionEventHandler {

    private static final Logger log = LoggerFactory.getLogger(TransactionEventHandler.class);

    private final CreateTransactionUseCase useCase;

    public TransactionEventHandler(CreateTransactionUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "CreateTransactionUseCase cannot be null");
    }

    public void handle(ExternalTransactionEvent event) {
        Objects.requireNonNull(event, "Event cannot be null");

        log.info("Processing transaction event externalId={}, correlationId={}", event.externalId(), event.correlationId());

        CreateTransactionCommand command = new CreateTransactionCommand(
                event.amount(),
                event.currency(),
                event.type(),
                event.description()
        );

        useCase.execute(command);
    }
}
