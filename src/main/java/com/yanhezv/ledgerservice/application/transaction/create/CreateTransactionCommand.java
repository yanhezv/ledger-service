package com.yanhezv.ledgerservice.application.transaction.create;

import java.math.BigDecimal;

public record CreateTransactionCommand(
        BigDecimal amount,
        String currency,
        String type,
        String description
) {
}
