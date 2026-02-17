package br.com.payment_service.dtos;

import br.com.bookstore.commons.dto.Status;
import br.com.payment_service.entities.Payments;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PaymentCompletedEvent(
        BigDecimal amount,
        @JsonProperty("purchase_id")
        Long purchaseId,
        @JsonProperty("public_identifier")
        String customerId,
        @JsonProperty("payment_id")
        Long paymentId,
        Status status,
        String order
) {

    public Payments toEntity() {
        Payments payment = new Payments();
        payment.setAmount(amount);
        payment.setPurchaseId(purchaseId);
        payment.setCustomerId(customerId);
        return payment;
    }
}