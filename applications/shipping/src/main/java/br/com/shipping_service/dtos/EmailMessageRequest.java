package br.com.shipping_service.dtos;

import br.com.shipping_service.entities.EmailTemplate;

public record EmailMessageRequest(
        String publicIdentifier,
        String book,
        String shippingId,
        EmailTemplate template
) {
}