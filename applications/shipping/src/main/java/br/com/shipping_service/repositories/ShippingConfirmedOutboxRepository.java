package br.com.shipping_service.repositories;

import br.com.shipping_service.entities.ShippingConfirmedOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingConfirmedOutboxRepository extends JpaRepository<ShippingConfirmedOutbox, Long> {
}
