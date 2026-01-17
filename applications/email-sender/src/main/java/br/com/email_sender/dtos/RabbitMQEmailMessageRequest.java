package br.com.email_sender.dtos;

import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;

public record RabbitMQEmailMessageRequest(
        String publicIdentifier,
        String book,
        String status,
        EmailTemplate template
) {
    public EmailMessage toEntity() {
        return new EmailMessage(
                book,
                publicIdentifier
        );
    }

}