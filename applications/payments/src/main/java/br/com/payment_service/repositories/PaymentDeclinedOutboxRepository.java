package br.com.payment_service.repositories;

import br.com.payment_service.entities.PaymentDeclinedOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDeclinedOutboxRepository extends JpaRepository<PaymentDeclinedOutbox, Long> {
}
