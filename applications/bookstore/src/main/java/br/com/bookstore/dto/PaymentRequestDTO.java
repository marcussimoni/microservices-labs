package br.com.bookstore.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        BigDecimal amount,
        Long orderId,
        String customerId,
        String book
) {}