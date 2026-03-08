package br.com.shipping_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShippingRequestDTO(
        @JsonProperty("customer_id")
        String customerId,
        @JsonProperty("order_id")
        Long orderId
) {
}
