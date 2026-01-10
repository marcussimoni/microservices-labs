package br.com.payment_service.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENTS_QUEUE = "payments_queue";

    public static final String EMAIL_QUEUE = "email_queue";
    public static final String BOOKSTORE_QUEUE = "bookstore_queue";
    public static final String SHIPPING_QUEUE = "shipping_queue";
    public static final String PAYMENT_EXCHANGE = "payment_exchange";

    @Bean
    public Queue paymentsQueue() {
        return new Queue(PAYMENTS_QUEUE, true);
    }

    @Bean
    public Queue emailsQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Queue bookstoreQueue() {
        return new Queue(BOOKSTORE_QUEUE, true);
    }

    @Bean
    public Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE, true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(PAYMENT_EXCHANGE, true, true);
    }

    @Bean
    public Binding paymentBindingEmails(Queue emailsQueue, FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(emailsQueue)
                .to(fanoutExchange);
    }

    @Bean
    public Binding paymentBindingBookstore(Queue bookstoreQueue, FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(bookstoreQueue)
                .to(fanoutExchange);
    }

    @Bean
    public Binding paymentBindingShipping(Queue shippingQueue, FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(shippingQueue)
                .to(fanoutExchange);
    }

}