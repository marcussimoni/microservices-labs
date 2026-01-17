package br.com.shipping_service.controllers;

import br.com.shipping_service.dtos.KafkaOutboxMessage;
import br.com.shipping_service.dtos.ShippingRequestDTO;
import br.com.shipping_service.services.ShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static br.com.shipping_service.configs.RabbitMQConfig.SHIPPING_QUEUE;

@Component
public class ShippingListener {

    private final Logger log = LoggerFactory.getLogger(ShippingListener.class);
    private final ShippingService shippingService;
    private final ObjectMapper objectMapper;

    public ShippingListener(ShippingService shippingService, ObjectMapper objectMapper) {
        this.shippingService = shippingService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(topics = "postgres.payments.payment_confirmed_outbox", groupId = "shipping-service")
    public void receiveMessage(String message) throws JsonProcessingException {
        try {
            TypeReference<KafkaOutboxMessage<ShippingRequestDTO>> typeReference = new TypeReference<>() {
            };
            KafkaOutboxMessage<ShippingRequestDTO> kafkaOutboxMessage = objectMapper.readValue(message, typeReference);

            ShippingRequestDTO dto = kafkaOutboxMessage.getPayload();
            shippingService.prepareToDelivery(dto);
        } catch (Exception e) {
            throw e;
        }

    }
}