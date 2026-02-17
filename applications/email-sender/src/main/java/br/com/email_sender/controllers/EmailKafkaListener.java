package br.com.email_sender.controllers;

import br.com.bookstore.commons.dto.CourierQuote;
import br.com.bookstore.commons.dto.OrderStatus;
import br.com.bookstore.commons.dto.Shipping;
import br.com.bookstore.commons.utils.KafkaCommonsUtils;
import br.com.email_sender.dtos.KafkaEmailMessageRequest;
import br.com.email_sender.dtos.KafkaOutboxMessage;
import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;
import br.com.email_sender.services.EmailMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EmailKafkaListener {

    private final EmailMessageService emailMessageService;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(EmailKafkaListener.class);

    public EmailKafkaListener(EmailMessageService emailMessageService, ObjectMapper objectMapper) {
        this.emailMessageService = emailMessageService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(topics = "postgres.payments.payment_confirmed_outbox", groupId = "email-sender-service")
    public void receiveConfirmedMessage(String message) throws JsonProcessingException {
        try {
            log.info("Receiving confirmed payment event to process");

            TypeReference<KafkaOutboxMessage<KafkaEmailMessageRequest>> typeReference = new TypeReference<>() {
            };
            KafkaOutboxMessage<KafkaEmailMessageRequest> kafkaOutboxMessage = objectMapper.readValue(message, typeReference);

            KafkaEmailMessageRequest payload = kafkaOutboxMessage.getPayload();
            emailMessageService.save(payload.toEntity(), EmailTemplate.PAYMENT_STATUS);
        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

    @Transactional
    @KafkaListener(topics = "postgres.shipping.shipping_confirmed_outbox", groupId = "email-sender-payment-approved")
    public void receiveShippingConfirmedMessage(String message) throws JsonProcessingException {
        try {

            log.info("Receiving shipping confirmed event to process");
            Shipping payload = KafkaCommonsUtils.getPayload(message, Shipping.class);

            CourierQuote courierQuote = KafkaCommonsUtils.getObject(payload.courierPayload(), CourierQuote.class);

            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setStatus(String.format(OrderStatus.PAYMENT_APPROVED.getDescription(), courierQuote.companyName(), courierQuote.estDeliveryDays()));
            emailMessage.setPublicIdentifier(payload.publicIdentifier());

            emailMessageService.save(emailMessage, EmailTemplate.SHIPPING);

        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

}