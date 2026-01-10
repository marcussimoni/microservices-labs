package br.com.bookstore.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        BigDecimal amount,
        Long purchaseId,
        String publicIdentifier,
        String book
) {}