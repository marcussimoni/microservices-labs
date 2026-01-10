package br.com.bookstore.service;

import br.com.bookstore.config.RabbitMQConfig;
import br.com.bookstore.dto.EmailMessageRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@org.springframework.stereotype.Service
public class EmailQueueService {

    private final RabbitTemplate rabbitTemplate;

    public EmailQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(EmailMessageRequest dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, dto);
        System.out.println("📩 Sent email notification to queue: " + dto);
    }
}