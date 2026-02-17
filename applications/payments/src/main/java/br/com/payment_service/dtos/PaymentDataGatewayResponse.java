package br.com.payment_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentDataGatewayResponse(
        @JsonProperty("authorization_code")
        String authorizationCode,

        @JsonProperty("receipt_url")
        String receiptUrl
) {
}
