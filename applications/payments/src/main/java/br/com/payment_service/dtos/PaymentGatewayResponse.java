package br.com.payment_service.dtos;

import br.com.bookstore.commons.dto.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

public record PaymentGatewayResponse(
        boolean success,
        @JsonProperty("transaction_id")
        String transactionId,
        Status status,
        OffsetDateTime timestamp,
        PaymentDataGatewayResponse data,
        ErrorDetails error
) {
    public PaymentGatewayResponse {
        if (Objects.isNull(data)) {
            data = new PaymentDataGatewayResponse(null, null);
        }
        if (Objects.isNull(error)) {
            error = new ErrorDetails(null, null, null);
        }
    }
    public record ErrorDetails(
            String code,
            String message,
            Map<String, Object> details
    ) { }
}