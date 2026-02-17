package br.com.payment_service.services;

import br.com.payment_service.dtos.*;
import br.com.payment_service.entities.EmailTemplate;
import br.com.payment_service.entities.Payments;
import br.com.payment_service.repositories.PaymentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
public class PaymentsService {

    private final PaymentsRepository repository;
    private final PaymentGatewayService paymentGatewayService;
    private final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final PaymentOutboxService paymentOutboxService;

    public PaymentsService(PaymentsRepository repository, PaymentGatewayService paymentGatewayService, UserManagementService userManagementService, PaymentOutboxService paymentOutboxService) {
        this.repository = repository;
        this.paymentGatewayService = paymentGatewayService;
        this.paymentOutboxService = paymentOutboxService;
    }

    @Transactional
    public void save(PaymentCompletedEvent paymentDTO) {

        var payment = paymentDTO.toEntity();

        PaymentGatewayRequestDTO paymentGatewayRequestDTO = new PaymentGatewayRequestDTO(payment.getAmount());

        log.info("Sending payment to payment gateway");

        PaymentGatewayResponse paymentResponse = paymentGatewayService.sendPayment(paymentGatewayRequestDTO);

        createPaymentEvent(paymentResponse, payment);

    }

    private void savePayment(Payments payment) {
        log.info("Saving payment into database");
        repository.save(payment);
    }

    private void createPaymentEvent(PaymentGatewayResponse paymentResponse, Payments payment) {

        log.info("Saving payment event");
        payment.setPaymentAt(paymentResponse.timestamp());
        payment.setStatus(paymentResponse.status());
        payment.setTransactionId(paymentResponse.transactionId());
        payment.setAuthorizationCode(paymentResponse.data().authorizationCode());
        payment.setReceiptUrl(paymentResponse.data().receiptUrl());

        savePayment(payment);

        PaymentCompletedEvent paymentRequest = new PaymentCompletedEvent(
                payment.getAmount(), payment.getPurchaseId(), payment.getCustomerId(), payment.getId(), paymentResponse.status(), ""
        );

        log.info("Sending message to the payment-exchange");
        paymentOutboxService.savePaymentConfirmed(paymentRequest);

    }

    public List<Payments> findByPurchaseIds(List<Long> purchaseIds) {
        return repository.findByPurchaseIdIn(purchaseIds);
    }

    public List<PaymentGatewayResponse> findPayments() {
        return paymentGatewayService.listPaymentsGateway();
    }

}