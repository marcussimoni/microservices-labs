package br.com.shipping_service.dtos;

public record ShippingRequestDTO(
        String publicIdentifier,
        Long purchaseId
) {
}
