package br.com.bookstore.commons.dto;

public enum OrderStatus {

    ORDER_CREATED("Order created. Waiting payment confirmation."),
    PAYMENT_APPROVED("Payment approved. Waiting shipping confirmation"),
    PAYMENT_DECLINED("Payment declined. See the details for more information"),
    PAYMENT_CANCELLED("Payment cancelled. See the details for more information"),
    ORDER_SHIPPED("Your order was shipped. %s is responsible for the delivery. Estimated days is: %d");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
