package br.com.payment_service.dtos;

import br.com.bookstore.commons.dto.Status;
import br.com.payment_service.entities.Payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record PaymentResponseDTO(
        Long id,
        BigDecimal amount,
        OffsetDateTime paymentAt,
        Long purchaseId,
        Status status
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