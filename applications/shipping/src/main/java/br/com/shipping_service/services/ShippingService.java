package br.com.shipping_service.services;

import br.com.shipping_service.dtos.PurchaseDTO;
import br.com.shipping_service.dtos.ShippingRequestDTO;
import br.com.shipping_service.dtos.UserResponseDTO;
import br.com.shipping_service.entities.Shipping;
import br.com.shipping_service.entities.ShippingConfirmedOutbox;
import br.com.shipping_service.repositories.ShippingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingService {

    private final UserManagementService userManagementService;
    private final BookstoreService bookstoreService;
    private final ShippingRepository repository;
    private final ShippingConfirmedOutboxService shippingConfirmedOutboxService;

    public ShippingService(UserManagementService userManagementService, BookstoreService bookstoreService, ShippingRepository repository, ShippingConfirmedOutboxService shippingConfirmedOutboxService) {
        this.userManagementService = userManagementService;
        this.bookstoreService = bookstoreService;
        this.shippingConfirmedOutboxService = shippingConfirmedOutboxService;
        this.repository = repository;
    }

    @Transactional
    public void prepareToDelivery(ShippingRequestDTO dto) {

        UserResponseDTO user = userManagementService.getUserById(dto.publicIdentifier());
        PurchaseDTO purchase = bookstoreService.getPurchaseById(dto.purchaseId());

        Shipping shipping = new Shipping();
        shipping.setBook(purchase.book().title());
        shipping.setCity(user.city());
        shipping.setPublicIdentifier(user.publicIdentifier());

        repository.save(shipping);

        ShippingConfirmedOutbox shippingConfirmedOutbox = new ShippingConfirmedOutbox(
                purchase.totalPrice(), purchase.id(), user.publicIdentifier(), purchase.book().title(), purchase.status()
        );

        shippingConfirmedOutboxService.saveShippingOutbox(shippingConfirmedOutbox);

    }

    public Shipping findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));
    }


    public List<Shipping> findByPublicIdentifier(String publicIdentifier) {
        return repository.findByPublicIdentifier(publicIdentifier);
    }

}
