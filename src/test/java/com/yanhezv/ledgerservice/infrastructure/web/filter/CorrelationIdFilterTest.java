package com.yanhezv.ledgerservice.infrastructure.web.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest
@Import(CorrelationIdFilter.class)
class CorrelationIdFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGenerateCorrelationIdWhenMissing() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(header().exists("X-Correlation-Id"))
                .andExpect(header().string("X-Correlation-Id", matchesPattern("[0-9a-fA-F\\-]{36}")));
    }

    @Test
    void shouldReuseIncomingCorrelationId() throws Exception {
        mockMvc.perform(get("/").header("X-Correlation-Id", "test-id"))
                .andExpect(header().string("X-Correlation-Id", "test-id"));
    }
}
