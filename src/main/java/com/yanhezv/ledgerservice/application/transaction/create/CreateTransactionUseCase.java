package com.yanhezv.ledgerservice.application.transaction.create;

import com.yanhezv.ledgerservice.application.port.TransactionRepository;
import com.yanhezv.ledgerservice.domain.model.Currency;
import com.yanhezv.ledgerservice.domain.model.Money;
import com.yanhezv.ledgerservice.domain.model.Transaction;
import com.yanhezv.ledgerservice.domain.model.TransactionType;

import java.util.Objects;

public class CreateTransactionUseCase {

    private final TransactionRepository repository;

    public CreateTransactionUseCase(TransactionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TransactionRepository cannot be null");
    }

    public Transaction execute(CreateTransactionCommand command) {
        Objects.requireNonNull(command, "Command cannot be null");

        Currency currency = Currency.of(command.currency());
        Money money = Money.of(command.amount(), currency);
        TransactionType type = TransactionType.from(command.type());

        Transaction transaction = Transaction.create(money, type, command.description());

        return repository.save(transaction);
    }
}
