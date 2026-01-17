package br.com.shipping_service.services;

import br.com.shipping_service.entities.ShippingConfirmedOutbox;
import br.com.shipping_service.repositories.ShippingConfirmedOutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShippingConfirmedOutboxService {

    private final ShippingConfirmedOutboxRepository repository;

    public ShippingConfirmedOutboxService(ShippingConfirmedOutboxRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveShippingOutbox(ShippingConfirmedOutbox shippingConfirmedOutbox) {
        repository.save(shippingConfirmedOutbox);
    }
}
