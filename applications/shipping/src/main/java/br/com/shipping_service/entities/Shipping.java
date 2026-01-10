package br.com.shipping_service.entities;


import jakarta.persistence.*;
import org.springframework.data.annotation.Transient;

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

    @Transient
    private ShippingStatus status;

    @Column(name = "public_identifier")
    private String publicIdentifier;

    public Shipping() {
        this.sentAt = LocalDateTime.now();
        this.status = ShippingStatus.CREATED;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public ShippingStatus getStatus() {
        return status;
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
