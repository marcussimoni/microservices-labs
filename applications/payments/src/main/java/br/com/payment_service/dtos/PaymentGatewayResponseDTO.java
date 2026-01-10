package br.com.payment_service.dtos;

import br.com.payment_service.entities.Payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentGatewayResponseDTO(
        BigDecimal amount,
        Payments.Status status,
        LocalDateTime timestamp
) {
}