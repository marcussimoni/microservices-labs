package br.com.shipping_service.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String EMAIL_QUEUE = "email_queue";
    public static final String SHIPPING_QUEUE = "shipping_queue";

    @Bean
    public Queue emailsQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE, true);
    }

}