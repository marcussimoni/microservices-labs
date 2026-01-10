package br.com.shipping_service.repositories;

import br.com.shipping_service.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    List<Shipping> findByPublicIdentifier(String publicIdentifier);

}
