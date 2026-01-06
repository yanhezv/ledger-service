package com.yanhezv.ledgerservice.infrastructure.messaging.inbound;

import java.math.BigDecimal;

public record ExternalTransactionEvent(
        String externalId,
        BigDecimal amount,
        String currency,
        String type,
        String description,
        String correlationId
) {
}
