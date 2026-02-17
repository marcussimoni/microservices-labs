package br.com.bookstore.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Shipping(
    Long id,
    String state,
    String city,
    String book,
    LocalDateTime sentAt,
    @JsonProperty("public_identifier")
    String publicIdentifier,
    @JsonProperty("courier_payload")
    String courierPayload
) {}