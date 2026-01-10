package br.com.bookstore.dto;

import br.com.bookstore.model.EmailTemplate;

public record PaymentMessageRequest(
        Long purchaseId,
        String userName,
        String email,
        String book,
        String status,
        EmailTemplate template
) {
}