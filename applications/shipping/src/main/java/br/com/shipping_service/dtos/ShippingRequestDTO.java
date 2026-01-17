package br.com.shipping_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShippingRequestDTO(
        @JsonProperty("public_identifier")
        String publicIdentifier,
        @JsonProperty("purchase_id")
        Long purchaseId
) {
}
