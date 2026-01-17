package br.com.payment_service.repositories;

import br.com.payment_service.entities.PaymentConfirmedOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentConfirmedOutboxRepository extends JpaRepository<PaymentConfirmedOutbox, Long> {
}
