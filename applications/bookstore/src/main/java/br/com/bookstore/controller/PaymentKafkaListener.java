package br.com.bookstore.controller;

import br.com.bookstore.dto.KafkaOutboxMessage;
import br.com.bookstore.dto.PaymentMessageRequest;
import br.com.bookstore.model.EmailTemplate;
import br.com.bookstore.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentKafkaListener {

    private final PurchaseService purchaseService;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(PaymentKafkaListener.class);

    public PaymentKafkaListener(PurchaseService purchaseService, ObjectMapper objectMapper) {
        this.purchaseService = purchaseService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(topics = "postgres.payments.payment_confirmed_outbox", groupId = "booking-service")
    public void receiveMessage(String message) throws JsonProcessingException {
        try {
            TypeReference<KafkaOutboxMessage<PaymentMessageRequest>> typeReference = new TypeReference<>() {
            };
            KafkaOutboxMessage<PaymentMessageRequest> kafkaOutboxMessage = objectMapper.readValue(message, typeReference);

            PaymentMessageRequest dto = kafkaOutboxMessage.getPayload();
            purchaseService.updatePurchase(dto.purchaseId(), "Payment confirmed and awaiting shipment.");
        } catch (Exception e) {
            throw e;
        }

    }

    @Transactional
    @KafkaListener(topics = "postgres.shipping.shipping_confirmed_outbox", groupId = "bookstore-service")
    public void receiveShippingConfirmedMessage(String message) throws JsonProcessingException {
        try {
            log.info("Receiving shipping confirmed event to process");

            TypeReference<KafkaOutboxMessage<PaymentMessageRequest>> typeReference = new TypeReference<>() {
            };
            KafkaOutboxMessage<PaymentMessageRequest> kafkaOutboxMessage = objectMapper.readValue(message, typeReference);

            PaymentMessageRequest dto = kafkaOutboxMessage.getPayload();
            purchaseService.updatePurchase(dto.purchaseId(), "The order has been shipped.");
        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

}
