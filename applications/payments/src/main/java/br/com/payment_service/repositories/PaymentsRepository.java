package br.com.payment_service.repositories;

import br.com.payment_service.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    List<Payments> findByPurchaseIdIn(List<Long> purchaseIds);

}