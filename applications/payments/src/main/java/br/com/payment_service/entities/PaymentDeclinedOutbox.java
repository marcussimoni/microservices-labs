package br.com.payment_service.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
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

    public PaymentDeclinedOutbox() {
    }

    public PaymentDeclinedOutbox(Long purchaseId, String publicIdentifier, String status) {
        this.purchaseId = purchaseId;
        this.publicIdentifier = publicIdentifier;
        this.status = status;
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

}
