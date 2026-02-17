package br.com.shipping_service.dtos;

import br.com.shipping_service.entities.CourierQuote;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CourierQuoteResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("company_name")
    String companyName,

    @JsonProperty("price")
    Double price,

    @JsonProperty("rating")
    Double rating,

    @JsonProperty("est_delivery_days")
    Integer estDeliveryDays,

    @JsonProperty("max_weight_kg")
    Integer maxWeightKg,

    @JsonProperty("tracking_available")
    Boolean isTrackingAvailable,

    @JsonProperty("region")
    String region
) {
    public CourierQuote toCourierQuote() {
        return new CourierQuote(
                id(),
                companyName(),
                price(),
                rating(),
                estDeliveryDays(),
                maxWeightKg(),
                isTrackingAvailable(),
                region()
        );
    }
}