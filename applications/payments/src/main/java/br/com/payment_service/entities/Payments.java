package br.com.payment_service.entities;

import br.com.bookstore.commons.dto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_at", nullable = false)
    private OffsetDateTime paymentAt;

    @Column(name = "purchase_id", nullable = false)
    private Long purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @Column(name = "customer_id")
    private String customerId;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}