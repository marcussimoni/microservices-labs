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
@Table(name = "payment_confirmed_outbox")
public class PaymentConfirmedOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Column(name = "public_identifier")
    private String publicIdentifier;
    private String book;
    private String status;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    public PaymentConfirmedOutbox(BigDecimal amount, Long purchaseId, String publicIdentifier, String book, String status) {
        this.id = id;
        this.amount = amount;
        this.purchaseId = purchaseId;
        this.publicIdentifier = publicIdentifier;
        this.book = book;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.idempotencyKey = Generators.defaultTimeBasedGenerator().generate();
    }
}
