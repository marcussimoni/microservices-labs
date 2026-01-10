package br.com.payment_service.dtos;

import java.math.BigDecimal;

public record PaymentGatewayRequestDTO(
        BigDecimal amount
) {
}