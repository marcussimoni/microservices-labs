package br.com.bookstore.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CourierQuote(
    @JsonProperty("id") Long id,
    @JsonProperty("company_name") String companyName,
    @JsonProperty("price") Double price,
    @JsonProperty("rating") Double rating,
    @JsonProperty("est_delivery_days") Integer estDeliveryDays,
    @JsonProperty("max_weight_kg") Integer maxWeightKg,
    @JsonProperty("tracking_available") Boolean isTrackingAvailable,
    @JsonProperty("region") String region
) {}