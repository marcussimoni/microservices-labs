package br.com.email_sender.dtos;

import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;

public record EmailMessageRequest(
        String publicIdentifier,
        String book,
        String status,
        EmailTemplate template
) {
    public EmailMessage toEntity() {
        return new EmailMessage(
                publicIdentifier,
                book,
                status
        );
    }

}