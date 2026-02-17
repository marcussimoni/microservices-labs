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
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Column(name = "public_identifier")
    private String publicIdentifier;
    private String status;
    private BigDecimal amount;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    public PaymentDeclinedOutbox(Long purchaseId, String publicIdentifier, String status, BigDecimal amount) {
        this.purchaseId = purchaseId;
        this.publicIdentifier = publicIdentifier;
        this.status = status;
        this.amount = amount;
    }

    @PrePersist
    public void prePersist() {
        this.idempotencyKey = Generators.defaultTimeBasedGenerator().generate();
    }

}
