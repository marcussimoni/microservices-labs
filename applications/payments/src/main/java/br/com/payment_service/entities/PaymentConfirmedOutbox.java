package br.com.payment_service.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
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

    public PaymentConfirmedOutbox() {
    }

    public PaymentConfirmedOutbox(BigDecimal amount, Long purchaseId, String publicIdentifier, String book, String status) {
        this.amount = amount;
        this.purchaseId = purchaseId;
        this.publicIdentifier = publicIdentifier;
        this.book = book;
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
