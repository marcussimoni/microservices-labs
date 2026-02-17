package br.com.payment_service.controllers;

import br.com.bookstore.commons.utils.KafkaCommonsUtils;
import br.com.payment_service.dtos.PaymentCompletedEvent;
import br.com.payment_service.services.PaymentsService;
import br.com.payment_service.services.ProcessedEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentsListener {

    private final PaymentsService service;
    private final ProcessedEventService processedEventService;
    private final Logger log = LoggerFactory.getLogger(PaymentsListener.class);
    private final String PURCHASE_PROCESS = "PURCHASE_PROCESS";

    @Transactional
    @KafkaListener(topics = "postgres.bookstore.purchase_outbox", groupId = "payment-service-purchases")
    public void receiveMessage(String message, MessageHeaders headers) throws JsonProcessingException {
        try {

            UUID idempotencyKey = KafkaCommonsUtils.getIdempotencyKey(headers);
            boolean isProcessed = processedEventService.eventProcessed(idempotencyKey, PURCHASE_PROCESS);

            if(isProcessed) {
                log.warn("Message already processed to key: {}", idempotencyKey);
                return;
            }

            log.info("Receiving message to process");

            PaymentCompletedEvent payload = KafkaCommonsUtils.getPayload(message, PaymentCompletedEvent.class);

            service.save(payload);

            log.info("Message processed successfully");

        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

}