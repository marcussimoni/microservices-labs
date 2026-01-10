package br.com.payment_service.dtos;

import br.com.payment_service.entities.Payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime paymentAt,
        Long purchaseId,
        Payments.Status status
) {

    public static PaymentResponseDTO fromEntity(Payments entity) {
        return new PaymentResponseDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getPaymentAt(),
                entity.getPurchaseId(),
                entity.getStatus()
        );
    }
}