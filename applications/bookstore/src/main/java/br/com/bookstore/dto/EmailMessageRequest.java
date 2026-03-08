package br.com.bookstore.dto;

import br.com.bookstore.model.EmailTemplate;

public record EmailMessageRequest(
        String customerId,
        String book,
        EmailTemplate template
) {
}