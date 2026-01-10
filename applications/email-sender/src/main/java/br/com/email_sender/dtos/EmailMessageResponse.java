package br.com.email_sender.dtos;

import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;

public record EmailMessageResponse(
        String userName,
        String email,
        String book,
        EmailTemplate template
) {
    public static EmailMessageResponse fromEntity(EmailMessage entity) {
        return new EmailMessageResponse(
                entity.getEmail(),
                entity.getUserName(),
                entity.getBook(),
                entity.getTemplate()
        );
    }
}

