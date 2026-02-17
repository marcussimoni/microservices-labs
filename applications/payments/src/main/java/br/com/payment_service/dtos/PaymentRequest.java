package br.com.payment_service.dtos;

import br.com.bookstore.commons.dto.Status;
import br.com.payment_service.entities.EmailTemplate;

import java.math.BigDecimal;

public record PaymentRequest(
        Long purchaseId,
        String publicIdentifier,
        String book,
        String status,
        BigDecimal amount,
        Status paymentStatus,
        EmailTemplate template
) {
}