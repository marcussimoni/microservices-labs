package br.com.email_sender.dtos;

import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;

public record KafkaEmailMessageRequest(
        @JsonProperty("customer_id")
        String customerId,
        String book,
        String status,
        EmailTemplate template
) {
    public EmailMessage toEntity() {
        return new EmailMessage(
                book,
                customerId,
                status
        );
    }

}