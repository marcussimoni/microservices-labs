package br.com.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEventId implements Serializable {
    @Column(name = "idempotency_key")
    private UUID idempotencyKey;

    @Column(name = "handler_name")
    private String handlerName;
}