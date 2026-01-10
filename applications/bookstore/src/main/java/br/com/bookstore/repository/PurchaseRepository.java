package br.com.bookstore.repository;

import br.com.bookstore.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerIdOrderByPurchaseDateDesc(String customerId);
}
