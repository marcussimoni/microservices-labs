package br.com.shipping_service.services;

import br.com.shipping_service.configs.RabbitMQConfig;
import br.com.shipping_service.dtos.EmailMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@org.springframework.stereotype.Service
public class EmailQueueService {

    private final RabbitTemplate rabbitTemplate;
    private final Logger log = LoggerFactory.getLogger(EmailQueueService.class);

    public EmailQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(EmailMessageRequest dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, dto);
        log.info("📩 Sent email notification to queue: {}", dto);
    }
}