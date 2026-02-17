package br.com.bookstore.controller;

import br.com.bookstore.commons.dto.CourierQuote;
import br.com.bookstore.commons.dto.OrderStatus;
import br.com.bookstore.commons.utils.KafkaCommonsUtils;
import br.com.bookstore.dto.KafkaOutboxMessage;
import br.com.bookstore.dto.PaymentMessageRequest;
import br.com.bookstore.model.EmailTemplate;
import br.com.bookstore.repository.ProcessedEventRepository;
import br.com.bookstore.service.ProcessedEventService;
import br.com.bookstore.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static br.com.bookstore.commons.dto.OrderStatus.ORDER_SHIPPED;

@Service
@RequiredArgsConstructor
public class PaymentKafkaListener {

    private static final String PURCHASE_PROCESS_DECLINED = "PURCHASE_PROCESS_DECLINED";
    private static final String PURCHASE_PROCESS_ACCEPTED = "PURCHASE_PROCESS_ACCEPTED";
    private static final String ORDER_SHIPPED = "ORDER_SHIPPED";
    public static final String MESSAGE_ALREADY_PROCESSED_TO_KEY = "Message already processed to key: {}";
    private final PurchaseService purchaseService;
    private final ProcessedEventService processedEventService;
    private final Logger log = LoggerFactory.getLogger(PaymentKafkaListener.class);

    @Transactional
    @KafkaListener(topics = "postgres.payments.payment_confirmed_outbox", groupId = "bookstore-approved-payment")
    public void paymentApproved(String message, MessageHeaders headers) throws JsonProcessingException {
        try {

            log.info("Payment accepted. Processing event");

            UUID idempotencyKey = KafkaCommonsUtils.getIdempotencyKey(headers);
            boolean isProcessed = processedEventService.eventProcessed(idempotencyKey, PURCHASE_PROCESS_ACCEPTED);

            if(isProcessed) {
                log.warn(MESSAGE_ALREADY_PROCESSED_TO_KEY, idempotencyKey);
                return;
            }

            PaymentMessageRequest dto = KafkaCommonsUtils.getPayload(message, PaymentMessageRequest.class);
            purchaseService.updatePurchase(dto.purchaseId(), OrderStatus.PAYMENT_APPROVED.getDescription());

        } catch (Exception e) {
            throw e;
        }

    }

    @Transactional
    @KafkaListener(topics = "postgres.payments.payment_declined_outbox", groupId = "bookstore-declined-payment")
    public void paymentDeclined(String message, MessageHeaders headers) throws JsonProcessingException {
        try {

            log.info("Payment declined. Processing event");

            UUID idempotencyKey = KafkaCommonsUtils.getIdempotencyKey(headers);
            boolean isProcessed = processedEventService.eventProcessed(idempotencyKey, PURCHASE_PROCESS_DECLINED);

            if(isProcessed) {
                log.warn(MESSAGE_ALREADY_PROCESSED_TO_KEY, idempotencyKey);
                return;
            }
            
            PaymentMessageRequest dto = KafkaCommonsUtils.getPayload(message, PaymentMessageRequest.class);
            purchaseService.updatePurchase(dto.purchaseId(), OrderStatus.PAYMENT_DECLINED.getDescription());

        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }

    @Transactional
    @KafkaListener(topics = "postgres.shipping.shipping_confirmed_outbox", groupId = "bookstore-order-shipped")
    public void receiveShippingConfirmedMessage(String message, MessageHeaders headers) {

        try {

            log.info("Order shipped. Processing event");

            UUID idempotencyKey = KafkaCommonsUtils.getIdempotencyKey(headers);
            boolean isProcessed = processedEventService.eventProcessed(idempotencyKey, ORDER_SHIPPED);

            if(isProcessed) {
                log.warn(MESSAGE_ALREADY_PROCESSED_TO_KEY, idempotencyKey);
                return;
            }

            PaymentMessageRequest dto = KafkaCommonsUtils.getPayload(message, PaymentMessageRequest.class);

            CourierQuote quote = KafkaCommonsUtils.getObject(dto.courierPayload(), CourierQuote.class);

            var status = String.format(OrderStatus.ORDER_SHIPPED.getDescription(), quote.companyName(), quote.estDeliveryDays());

            purchaseService.updatePurchase(dto.purchaseId(), status);

        } catch (Exception e) {
            log.error("Error during the process of event from listener", e);
            throw e;
        }

    }


}
