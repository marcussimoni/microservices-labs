package br.com.payment_service.dtos;

import br.com.payment_service.entities.EmailTemplate;

public record PaymentMessageRequest(
        Long purchaseId,
        String publicIdentifier,
        String book,
        String status,
        EmailTemplate template
) {
}