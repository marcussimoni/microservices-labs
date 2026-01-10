package br.com.payment_service.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_at", nullable = false)
    private LocalDateTime paymentAt;

    @Column(name = "purchase_id", nullable = false)
    private Long purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        PAID,
        DENIED
    }

    // Constructors
    public Payments() {}

    public Payments(BigDecimal amount, LocalDateTime paymentAt, Long purchaseId, Status status) {
        this.amount = amount;
        this.paymentAt = paymentAt;
        this.purchaseId = purchaseId;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPaymentAt() { return paymentAt; }

    public void setPaymentAt(LocalDateTime paymentAt) { this.paymentAt = paymentAt; }

    public Long getPurchaseId() { return purchaseId; }

    public void setPurchaseId(Long purchaseId) { this.purchaseId = purchaseId; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}