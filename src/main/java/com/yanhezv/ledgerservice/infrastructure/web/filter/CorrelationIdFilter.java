package com.yanhezv.ledgerservice.infrastructure.web.filter;

import com.yanhezv.ledgerservice.infrastructure.observability.CorrelationIdProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String correlationId = request.getHeader(CorrelationIdProvider.HEADER_NAME);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = CorrelationIdProvider.generate();
        }

        MDC.put(CorrelationIdProvider.MDC_KEY, correlationId);
        response.setHeader(CorrelationIdProvider.HEADER_NAME, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
