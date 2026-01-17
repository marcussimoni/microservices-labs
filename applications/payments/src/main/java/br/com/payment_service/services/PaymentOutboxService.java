package br.com.payment_service.services;

import br.com.payment_service.dtos.PaymentRequest;
import br.com.payment_service.entities.PaymentConfirmedOutbox;
import br.com.payment_service.entities.PaymentDeclinedOutbox;
import br.com.payment_service.entities.Payments;
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
    public void savePaymentConfirmed(PaymentRequest request) {

        log.info("Preparing to send event");

        if (request.paymentStatus().equals(Payments.Status.PAID)) {
            PaymentConfirmedOutbox paymentConfirmedOutbox = new PaymentConfirmedOutbox(
                    request.amount(), request.purchaseId(),
                    request.publicIdentifier(), request.book(),
                    request.status()
            );
            paymentConfirmedOutboxRepository.save(paymentConfirmedOutbox);
            log.info("Payment confirmed");
        }
        if (request.paymentStatus().equals(Payments.Status.DENIED)) {
            PaymentDeclinedOutbox paymentDeclinedOutbox = new PaymentDeclinedOutbox(request.purchaseId(), request.publicIdentifier(), request.status());
            paymentDeclinedOutboxRepository.save(paymentDeclinedOutbox);
            log.info("Payment declined");
        }

    }
}
