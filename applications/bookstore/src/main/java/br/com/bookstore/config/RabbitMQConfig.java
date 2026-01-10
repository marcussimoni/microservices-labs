package br.com.bookstore.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENTS_QUEUE = "payments_queue";
    public static final String EMAIL_QUEUE = "email_queue";
    public static final String BOOKSTORE_QUEUE = "bookstore_queue";

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

}