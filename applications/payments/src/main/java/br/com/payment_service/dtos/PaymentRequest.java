package br.com.payment_service.dtos;

import br.com.payment_service.entities.EmailTemplate;
import br.com.payment_service.entities.Payments;

import java.math.BigDecimal;

public record PaymentRequest(
        Long purchaseId,
        String publicIdentifier,
        String book,
        String status,
        BigDecimal amount,
        Payments.Status paymentStatus,
        EmailTemplate template
) {
}