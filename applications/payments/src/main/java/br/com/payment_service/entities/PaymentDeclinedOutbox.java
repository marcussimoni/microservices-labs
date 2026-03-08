package br.com.payment_service.entities;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payment_declined_outbox")
public class PaymentDeclinedOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "customer_id")
    private String customerId;
    private String status;
    private BigDecimal amount;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    public PaymentDeclinedOutbox(Long orderId, String customerId, String status, BigDecimal amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
    }

    @PrePersist
    public void prePersist() {
        this.idempotencyKey = Generators.defaultTimeBasedGenerator().generate();
    }

}
