package br.com.shipping_service.services;

import br.com.shipping_service.dtos.CourierQuoteResponse;
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
    private final CourierService courierService;
    private final ShippingRepository repository;
    private final ShippingConfirmedOutboxService shippingConfirmedOutboxService;

    public ShippingService(UserManagementService userManagementService, BookstoreService bookstoreService, CourierService courierService, ShippingRepository repository, ShippingConfirmedOutboxService shippingConfirmedOutboxService) {
        this.userManagementService = userManagementService;
        this.bookstoreService = bookstoreService;
        this.courierService = courierService;
        this.shippingConfirmedOutboxService = shippingConfirmedOutboxService;
        this.repository = repository;
    }

    @Transactional
    public void prepareToDelivery(ShippingRequestDTO dto) {

        UserResponseDTO user = userManagementService.getUserById(dto.publicIdentifier());
        PurchaseDTO purchase = bookstoreService.getPurchaseById(dto.purchaseId());
        CourierQuoteResponse courierQuoteResponse = courierService.courierQuote();

        final var courierQuote = courierQuoteResponse.toCourierQuote();

        Shipping shipping = new Shipping();
        shipping.setBook(purchase.book().title());
        shipping.setCity(user.city());
        shipping.setPublicIdentifier(user.publicIdentifier());
        shipping.setCourierPayload(courierQuote);

        repository.save(shipping);

        ShippingConfirmedOutbox shippingConfirmedOutbox = new ShippingConfirmedOutbox(
                purchase.totalPrice(), purchase.id(), user.publicIdentifier(),
                purchase.book().title(), purchase.status(), courierQuote
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
