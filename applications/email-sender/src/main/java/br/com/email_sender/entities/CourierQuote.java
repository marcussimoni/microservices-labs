package br.com.email_sender.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourierQuote {

    public CourierQuote() {
    }

    public CourierQuote(Long id, String companyName, Double price, Double rating,
                        Integer estDeliveryDays, Integer maxWeightKg,
                        Boolean isTrackingAvailable, String region) {
        this.id = id;
        this.companyName = companyName;
        this.price = price;
        this.rating = rating;
        this.estDeliveryDays = estDeliveryDays;
        this.maxWeightKg = maxWeightKg;
        this.isTrackingAvailable = isTrackingAvailable;
        this.region = region;
    }

    @JsonProperty("id")
    private Long id;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("est_delivery_days")
    private Integer estDeliveryDays;

    @JsonProperty("max_weight_kg")
    private Integer maxWeightKg;

    @JsonProperty("tracking_available")
    private Boolean isTrackingAvailable;

    @JsonProperty("region")
    private String region;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getEstDeliveryDays() {
        return estDeliveryDays;
    }

    public void setEstDeliveryDays(Integer estDeliveryDays) {
        this.estDeliveryDays = estDeliveryDays;
    }

    public Integer getMaxWeightKg() {
        return maxWeightKg;
    }

    public void setMaxWeightKg(Integer maxWeightKg) {
        this.maxWeightKg = maxWeightKg;
    }

    public Boolean getTrackingAvailable() {
        return isTrackingAvailable;
    }

    public void setTrackingAvailable(Boolean trackingAvailable) {
        isTrackingAvailable = trackingAvailable;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}