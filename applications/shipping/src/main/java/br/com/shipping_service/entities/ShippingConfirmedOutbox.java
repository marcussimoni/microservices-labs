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
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "customer_id")
    private String customerId;
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

    public ShippingConfirmedOutbox(BigDecimal amount, Long orderId, String customerId, String book, String status, CourierQuote courierPayload) {
        this.amount = amount;
        this.orderId = orderId;
        this.customerId = customerId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
