package com.yanhezv.ledgerservice.infrastructure.observability;

import java.util.UUID;

public final class CorrelationIdProvider {

    public static final String HEADER_NAME = "X-Correlation-Id";
    public static final String MDC_KEY = "correlationId";

    private CorrelationIdProvider() {
    }

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
