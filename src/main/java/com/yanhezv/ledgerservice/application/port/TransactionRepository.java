package com.yanhezv.ledgerservice.application.port;

import com.yanhezv.ledgerservice.domain.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(UUID id);

    List<Transaction> findAll();
}
