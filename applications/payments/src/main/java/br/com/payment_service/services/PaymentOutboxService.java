package br.com.payment_service.services;

import br.com.bookstore.commons.dto.Status;
import br.com.payment_service.dtos.PaymentCompletedEvent;
import br.com.payment_service.entities.PaymentConfirmedOutbox;
import br.com.payment_service.entities.PaymentDeclinedOutbox;
import br.com.payment_service.repositories.PaymentConfirmedOutboxRepository;
import br.com.payment_service.repositories.PaymentDeclinedOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentOutboxService {

    private final PaymentConfirmedOutboxRepository paymentConfirmedOutboxRepository;
    private final PaymentDeclinedOutboxRepository paymentDeclinedOutboxRepository;
    private final Logger log = LoggerFactory.getLogger(PaymentOutboxService.class);

    public PaymentOutboxService(PaymentConfirmedOutboxRepository paymentConfirmedOutboxRepository, PaymentDeclinedOutboxRepository paymentDeclinedOutboxRepository) {
        this.paymentConfirmedOutboxRepository = paymentConfirmedOutboxRepository;
        this.paymentDeclinedOutboxRepository = paymentDeclinedOutboxRepository;
    }

    @Transactional
    public void savePaymentConfirmed(PaymentCompletedEvent event) {

        log.info("Preparing to send event");

        if (event.status().equals(Status.APPROVED)) {
            var paymentConfirmedOutbox = new PaymentConfirmedOutbox(
                event.amount(), event.purchaseId(), event.customerId(), "", event.status().toString()
            );
            paymentConfirmedOutboxRepository.save(paymentConfirmedOutbox);
            log.info("Payment confirmed");
        } else {
            var paymentDeclinedOutbox = new PaymentDeclinedOutbox(
                    event.purchaseId(), event.customerId(), event.status().toString(), event.amount()
            );
            paymentDeclinedOutboxRepository.save(paymentDeclinedOutbox);
            log.info("Payment declined");
        }

    }
}
