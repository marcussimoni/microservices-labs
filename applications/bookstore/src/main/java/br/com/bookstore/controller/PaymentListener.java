package br.com.bookstore.controller;

import br.com.bookstore.dto.PaymentMessageRequest;
import br.com.bookstore.service.PurchaseService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static br.com.bookstore.config.RabbitMQConfig.BOOKSTORE_QUEUE;

@Service
public class PaymentListener {

    private final PurchaseService purchaseService;

    public PaymentListener(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RabbitListener(queues = BOOKSTORE_QUEUE)
    public void purchaseListener(PaymentMessageRequest dto) {

        purchaseService.updatePurchase(dto.purchaseId(), dto.status());

    }

}
