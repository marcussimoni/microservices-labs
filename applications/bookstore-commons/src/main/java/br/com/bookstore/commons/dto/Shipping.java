package br.com.bookstore.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Shipping(
    Long id,
    String state,
    String city,
    String book,
    LocalDateTime sentAt,
    @JsonProperty("customer_id")
    String customerId,
    @JsonProperty("courier_payload")
    String courierPayload
) {}