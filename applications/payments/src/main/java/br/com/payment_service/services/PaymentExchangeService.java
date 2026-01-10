package br.com.payment_service.services;

import br.com.payment_service.configs.RabbitMQConfig;
import br.com.payment_service.dtos.PaymentMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentExchangeService {

    private final RabbitTemplate rabbitTemplate;
    private final Logger log = LoggerFactory.getLogger(PaymentExchangeService.class);

    public PaymentExchangeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(PaymentMessageRequest dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_EXCHANGE, "", dto);
        log.info("📩 Sent to payment exchange notification to queue: {}", dto);
    }
}