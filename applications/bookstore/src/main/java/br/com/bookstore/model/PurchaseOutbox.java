package br.com.bookstore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
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

    public PurchaseOutbox() {
    }

    public PurchaseOutbox(Purchase purchase, String userIdentifier) {
        this.amount = purchase.getTotalPrice();
        this.book = purchase.getBook().getTitle();
        this.purchaseId = purchase.getId();
        this.publicIdentifier = userIdentifier;
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
