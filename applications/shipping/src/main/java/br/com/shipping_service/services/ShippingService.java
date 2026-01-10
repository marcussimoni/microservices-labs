package br.com.shipping_service.services;

import br.com.shipping_service.dtos.EmailMessageRequest;
import br.com.shipping_service.dtos.PurchaseDTO;
import br.com.shipping_service.dtos.ShippingRequestDTO;
import br.com.shipping_service.dtos.UserResponseDTO;
import br.com.shipping_service.entities.EmailTemplate;
import br.com.shipping_service.entities.Shipping;
import br.com.shipping_service.repositories.ShippingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingService {

    private final UserManagementService userManagementService;
    private final BookstoreService bookstoreService;
    private final ShippingRepository repository;
    private final EmailQueueService emailQueueService;

    public ShippingService(UserManagementService userManagementService, BookstoreService bookstoreService, ShippingRepository repository, EmailQueueService emailQueueService) {
        this.userManagementService = userManagementService;
        this.bookstoreService = bookstoreService;
        this.emailQueueService = emailQueueService;
        this.repository = repository;
    }

    @Transactional
    public void prepareToDelivery(ShippingRequestDTO dto) {

        UserResponseDTO user = userManagementService.getUserById(dto.publicIdentifier());
        PurchaseDTO purchase = bookstoreService.getPurchaseById(dto.purchaseId());

        Shipping shipping = new Shipping();
        shipping.setBook(purchase.book().title());
        shipping.setCity(user.city());
        shipping.setState(user.state());
        shipping.setPublicIdentifier(user.publicIdentifier());

        Shipping save = repository.save(shipping);

        EmailMessageRequest emailMessageRequest = new EmailMessageRequest(user.publicIdentifier(), purchase.book().title(), save.getId().toString(), EmailTemplate.SHIPPING);

        emailQueueService.sendToQueue(emailMessageRequest);

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
