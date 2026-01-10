package br.com.shipping_service.controllers;

import br.com.shipping_service.dtos.ShippingRequestDTO;
import br.com.shipping_service.services.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.shipping_service.configs.RabbitMQConfig.SHIPPING_QUEUE;

@Component
public class ShippingListener {

    private final Logger log = LoggerFactory.getLogger(ShippingListener.class);
    private final ShippingService shippingService;

    public ShippingListener(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @RabbitListener(queues = SHIPPING_QUEUE)
    public void receiveMessage(ShippingRequestDTO message) {
        log.info("Receiving message to process");
        shippingService.prepareToDelivery(message);
    }

}