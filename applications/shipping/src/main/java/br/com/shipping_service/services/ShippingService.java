package br.com.shipping_service.services;

import br.com.shipping_service.dtos.CourierQuoteResponse;
import br.com.shipping_service.dtos.OrderDTO;
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

        UserResponseDTO user = userManagementService.getUserById(dto.customerId());
        OrderDTO order = bookstoreService.getOrderById(dto.orderId());
        CourierQuoteResponse courierQuoteResponse = courierService.courierQuote();

        final var courierQuote = courierQuoteResponse.toCourierQuote();

        Shipping shipping = new Shipping();
        shipping.setBook(order.book().title());
        shipping.setCity(user.city());
        shipping.setCustomerId(user.customerId());
        shipping.setCourierPayload(courierQuote);

        repository.save(shipping);

        ShippingConfirmedOutbox shippingConfirmedOutbox = new ShippingConfirmedOutbox(
                order.totalPrice(), order.id(), user.customerId(),
                order.book().title(), order.status(), courierQuote
        );

        shippingConfirmedOutboxService.saveShippingOutbox(shippingConfirmedOutbox);

    }

    public Shipping findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));
    }


    public List<Shipping> findByPublicIdentifier(String customerId) {
        return repository.findByCustomerId(customerId);
    }

}
