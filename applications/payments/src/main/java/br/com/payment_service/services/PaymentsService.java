package br.com.payment_service.services;

import br.com.payment_service.dtos.*;
import br.com.payment_service.entities.EmailTemplate;
import br.com.payment_service.entities.Payments;
import br.com.payment_service.repositories.PaymentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentsService {

    private final PaymentsRepository repository;
    private final PaymentGatewayService paymentGatewayService;
    private final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final UserManagementService userManagementService;
    private final PaymentOutboxService paymentOutboxService;

    public PaymentsService(PaymentsRepository repository, PaymentGatewayService paymentGatewayService, UserManagementService userManagementService, PaymentOutboxService paymentOutboxService) {
        this.repository = repository;
        this.paymentGatewayService = paymentGatewayService;
        this.userManagementService = userManagementService;
        this.paymentOutboxService = paymentOutboxService;
    }

    @Transactional
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

        PaymentRequest paymentRequest = new PaymentRequest(
                paymentDTO.purchaseId(), user.publicIdentifier(),
                paymentDTO.book(), paymentResponseDTO.status().name(),
                paymentDTO.amount(),
                payment.getStatus(),
                EmailTemplate.PAYMENT_STATUS);

        log.info("Sending message to the payment-exchange");
        paymentOutboxService.savePaymentConfirmed(paymentRequest);

        return saved;

    }

    public List<Payments> findByPurchaseIds(List<Long> purchaseIds) {
        return repository.findByPurchaseIdIn(purchaseIds);
    }

}