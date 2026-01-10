package br.com.email_sender.entities;

public enum EmailTemplate {

    ORDER_RECEIVED("order-received-template.ftl", "Order received"),
    PAYMENT_STATUS("payment-status-template.ftl", "Payment status"),
    SHIPPING("shipping-template.ftl", "Shipping update");


    private final String template;
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public String getSubject() {
        return subject;
    }
}
