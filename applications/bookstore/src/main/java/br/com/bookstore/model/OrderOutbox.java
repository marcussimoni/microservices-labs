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
@Table(name = "order_outbox")
public class OrderOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "customer_id")
    private String customerId;
    private String book;
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    public OrderOutbox(Order order, String userIdentifier) {
        this.amount = order.getTotalPrice();
        this.book = order.getBook().getTitle();
        this.orderId = order.getId();
        this.customerId = userIdentifier;
        this.idempotencyKey = Generators.timeBasedGenerator().generate();
    }

}
