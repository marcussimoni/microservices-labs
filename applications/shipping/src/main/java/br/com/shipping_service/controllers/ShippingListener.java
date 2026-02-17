package br.com.shipping_service.controllers;

import br.com.bookstore.commons.utils.KafkaCommonsUtils;
import br.com.shipping_service.dtos.ShippingRequestDTO;
import br.com.shipping_service.services.ShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ShippingListener {

    private final Logger log = LoggerFactory.getLogger(ShippingListener.class);
    private final ShippingService shippingService;
    private final ObjectMapper objectMapper;
    public static final String SHIPPING_SERVICE = "shipping-service";

    public ShippingListener(ShippingService shippingService, ObjectMapper objectMapper) {
        this.shippingService = shippingService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(
            id = SHIPPING_SERVICE,
            topics = "postgres.payments.payment_confirmed_outbox",
            groupId = SHIPPING_SERVICE)
    public void receiveMessage(String message, MessageHeaders headers) throws JsonProcessingException {
        try {

            ShippingRequestDTO dto = KafkaCommonsUtils.getPayload(message, ShippingRequestDTO.class);

            log.info("Received Message for Shipping: {}", dto.purchaseId());

            log.info("Preparing to process event: {}", dto);

            shippingService.prepareToDelivery(dto);

            log.info("Event successfully processed");

        } catch (Exception e) {
            log.error("Failed to process event {} Reason: {}", SHIPPING_SERVICE, e.getMessage());
            throw e;
        }

    }
}