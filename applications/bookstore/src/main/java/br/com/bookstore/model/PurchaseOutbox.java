package br.com.bookstore.model;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "purchase_outbox")
public class PurchaseOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Column(name = "public_identifier")
    private String publicIdentifier;
    private String book;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    public PurchaseOutbox(Purchase purchase, String userIdentifier) {
        this.amount = purchase.getTotalPrice();
        this.book = purchase.getBook().getTitle();
        this.purchaseId = purchase.getId();
        this.publicIdentifier = userIdentifier;
        this.idempotencyKey = Generators.timeBasedGenerator().generate();
    }

}
