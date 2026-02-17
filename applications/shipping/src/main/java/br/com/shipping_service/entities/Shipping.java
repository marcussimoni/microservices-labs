package br.com.shipping_service.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    private String city;

    private String book;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "public_identifier")
    private String publicIdentifier;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "courier_payload")
    private CourierQuote courierPayload;

    public CourierQuote getCourierPayload() {
        return courierPayload;
    }

    public void setCourierPayload(CourierQuote courierPayload) {
        this.courierPayload = courierPayload;
    }

    public Shipping() {
        this.sentAt = LocalDateTime.now();
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public Long getId() {
        return id;
    }
}
