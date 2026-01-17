package br.com.payment_service.controllers;

import br.com.payment_service.dtos.KafkaOutboxMessage;
import br.com.payment_service.dtos.PaymentRequestDTO;
import br.com.payment_service.services.PaymentsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentsListener {

    private final PaymentsService service;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(PaymentsListener.class);

    public PaymentsListener(PaymentsService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(topics = "postgres.bookstore.purchase_outbox", groupId = "bookstore-service")
    public void receiveMessage(String message) throws JsonProcessingException {
        try {
            log.info("Receiving message to process");

            TypeReference<KafkaOutboxMessage<PaymentRequestDTO>> typeReference = new TypeReference<>() {
            };
            KafkaOutboxMessage<PaymentRequestDTO> kafkaOutboxMessage = objectMapper.readValue(message, typeReference);

            service.save(kafkaOutboxMessage.getPayload());
        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

}