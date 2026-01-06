package com.yanhezv.ledgerservice.application.transaction.list;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Transaction;

import java.util.List;
import java.util.Objects;

public class ListTransactionsUseCase {

    private final TransactionRepository repository;

    public ListTransactionsUseCase(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TransactionRepository cannot be null");
    }

    public List<Transaction> execute() {
        return repository.findAll();
    }
}
