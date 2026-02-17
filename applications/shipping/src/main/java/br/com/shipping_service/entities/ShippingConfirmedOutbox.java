package br.com.shipping_service.entities;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "shipping_confirmed_outbox")
public class ShippingConfirmedOutbox {

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
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "courier_payload")
    private CourierQuote courierPayload;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    @PrePersist
    public void prePersist() {
        this.idempotencyKey = Generators.timeBasedGenerator().generate();
    }

    public ShippingConfirmedOutbox() {
    }

    public ShippingConfirmedOutbox(BigDecimal amount, Long purchaseId, String publicIdentifier, String book, String status, CourierQuote courierPayload) {
        this.amount = amount;
        this.purchaseId = purchaseId;
        this.publicIdentifier = publicIdentifier;
        this.book = book;
        this.status = status;
        this.courierPayload = courierPayload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
