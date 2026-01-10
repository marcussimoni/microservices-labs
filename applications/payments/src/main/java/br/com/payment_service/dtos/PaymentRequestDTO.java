package br.com.payment_service.dtos;

import br.com.payment_service.entities.Payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestDTO(
        BigDecimal amount,
        Long purchaseId,
        String publicIdentifier,
        String book
) {

    public Payments toEntity() {
        Payments payment = new Payments();
        payment.setAmount(amount);
        payment.setPurchaseId(purchaseId);
        return payment;
    }
}