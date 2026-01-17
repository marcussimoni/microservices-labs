package br.com.bookstore.dto;

import br.com.bookstore.model.EmailTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentMessageRequest(
        @JsonProperty("purchase_id")
        Long purchaseId,
        String userName,
        String email,
        String book,
        String status,
        EmailTemplate template
) {
}