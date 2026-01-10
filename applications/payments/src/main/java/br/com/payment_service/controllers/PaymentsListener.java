package br.com.payment_service.controllers;

import br.com.payment_service.dtos.PaymentRequestDTO;
import br.com.payment_service.services.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.payment_service.configs.RabbitMQConfig.PAYMENTS_QUEUE;

@Component
public class PaymentsListener {

    private final PaymentsService service;
    private final Logger log = LoggerFactory.getLogger(PaymentsListener.class);

    public PaymentsListener(PaymentsService service) {
        this.service = service;
    }

    @RabbitListener(queues = PAYMENTS_QUEUE)
    public void receiveMessage(PaymentRequestDTO message) {
        log.info("Receiving message to process");
        service.save(message);
    }

}