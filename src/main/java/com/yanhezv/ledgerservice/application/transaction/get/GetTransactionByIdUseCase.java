package com.yanhezv.ledgerservice.application.transaction.get;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Transaction;

import java.util.Objects;
import java.util.UUID;

public class GetTransactionByIdUseCase {

    private final TransactionRepository repository;

    public GetTransactionByIdUseCase(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TransactionRepository cannot be null");
    }

    public Transaction execute(UUID transactionId) {
        Objects.requireNonNull(transactionId, "TransactionId cannot be null");

        return repository.findById(transactionId).orElseThrow(() ->
                new TransactionNotFoundException("Transaction not found: " + transactionId)
        );
    }
}
