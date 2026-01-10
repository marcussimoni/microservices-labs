package br.com.payment_service.services;

import br.com.payment_service.controllers.PaymentsListener;
import br.com.payment_service.dtos.*;
import br.com.payment_service.entities.EmailTemplate;
import br.com.payment_service.entities.Payments;
import br.com.payment_service.repositories.PaymentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {

    private final PaymentsRepository repository;
    private final PaymentGatewayService paymentGatewayService;
    private final PaymentExchangeService paymentExchangeService;
    private final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final UserManagementService userManagementService;

    public PaymentsService(PaymentsRepository repository, PaymentGatewayService paymentGatewayService, PaymentExchangeService paymentExchangeService, UserManagementService userManagementService) {
        this.repository = repository;
        this.paymentGatewayService = paymentGatewayService;
        this.paymentExchangeService = paymentExchangeService;
        this.userManagementService = userManagementService;
    }

    public Payments save(PaymentRequestDTO paymentDTO) {

        var payment = paymentDTO.toEntity();

        PaymentGatewayRequestDTO paymentGatewayRequestDTO = new PaymentGatewayRequestDTO(payment.getAmount());

        log.info("Sending payment to payment gateway");
        PaymentGatewayResponseDTO paymentResponseDTO = paymentGatewayService.sendPayment(paymentGatewayRequestDTO);
        payment.setStatus(paymentResponseDTO.status());
        payment.setPaymentAt(paymentResponseDTO.timestamp());

        log.info("Saving payment into database");
        Payments saved = repository.save(payment);

        UserResponseDTO user = userManagementService.getUserById(paymentDTO.publicIdentifier());

        PaymentMessageRequest emailQueueDTO = new PaymentMessageRequest(
                paymentDTO.purchaseId(), user.publicIdentifier(),
                paymentDTO.book(), paymentResponseDTO.status().name(),
                EmailTemplate.PAYMENT_STATUS);

        log.info("Sending message to the payment-exchange");
        paymentExchangeService.sendToQueue(emailQueueDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Error {0}", e);
        }

        return saved;

    }

    public List<Payments> findByPurchaseIds(List<Long> purchaseIds) {
        return repository.findByPurchaseIdIn(purchaseIds);
    }

}