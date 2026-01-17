package br.com.payment_service.dtos;

import br.com.payment_service.entities.Payments;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestDTO(
        BigDecimal amount,
        @JsonProperty("purchase_id")
        Long purchaseId,
        @JsonProperty("public_identifier")
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