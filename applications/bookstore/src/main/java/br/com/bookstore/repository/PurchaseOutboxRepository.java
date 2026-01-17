package br.com.bookstore.repository;

import br.com.bookstore.model.PurchaseOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOutboxRepository extends JpaRepository<PurchaseOutbox, Long> {
}
