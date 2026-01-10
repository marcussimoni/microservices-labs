package br.com.bookstore.service;

import br.com.bookstore.config.RabbitMQConfig;
import br.com.bookstore.dto.PaymentRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentsQueueService {

    private final RabbitTemplate rabbitTemplate;

    public PaymentsQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(PaymentRequestDTO dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENTS_QUEUE, dto);
        System.out.println("📩 Sent payment to queue: " + dto);
    }

}