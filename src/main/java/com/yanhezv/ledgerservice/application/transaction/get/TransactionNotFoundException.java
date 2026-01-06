package com.yanhezv.ledgerservice.application.transaction.get;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
