package br.com.bookstore.service;

import br.com.bookstore.dto.*;
import br.com.bookstore.model.Book;
import br.com.bookstore.model.EmailTemplate;
import br.com.bookstore.model.Purchase;
import br.com.bookstore.model.PurchaseOutbox;
import br.com.bookstore.repository.PurchaseOutboxRepository;
import br.com.bookstore.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseOutboxRepository purchaseOutboxRepository;
    private final BookService bookService;
    private final UserManagementService userManagementService;
    private final EmailQueueService emailQueueService;
    private final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            PurchaseOutboxRepository purchaseOutboxRepository,
            BookService bookService,
            UserManagementService userManagementService,
            EmailQueueService emailQueueService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseOutboxRepository = purchaseOutboxRepository;
        this.bookService = bookService;
        this.userManagementService = userManagementService;
        this.emailQueueService = emailQueueService;
    }

    @Transactional
    public PurchaseDTO purchaseBook(PurchaseRequest request) {

        Long bookId = request.bookId();

        log.info("Retrieving book by id");

        Book book = bookService.findBookById(bookId);

        BigDecimal totalPrice = book.getPrice();

        log.info("Retrieving authenticated user");
        UserResponseDTO authenticatedUser = userManagementService.getAuthenticatedUser();

        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setCustomerId(authenticatedUser.publicIdentifier());
        purchase.setQuantity(1);
        purchase.setTotalPrice(totalPrice);
        purchase.setStatus("WAITING APPROVAL");

        log.info("Saving purchase into database");
        Purchase savedPurchase = purchaseRepository.save(purchase);

        bookService.updateBookStock(bookId, -1);

        log.info("Saving outbox message to payment");
        PurchaseOutbox purchaseOutbox = new PurchaseOutbox(savedPurchase, authenticatedUser.publicIdentifier());
        purchaseOutboxRepository.save(purchaseOutbox);

        log.info("Send message to email-service");
        EmailMessageRequest emailMessageRequest = new EmailMessageRequest(authenticatedUser.publicIdentifier(), purchase.getBook().getTitle(), EmailTemplate.ORDER_RECEIVED);
        emailQueueService.sendToQueue(emailMessageRequest);

        return PurchaseDTO.fromEntity(savedPurchase);
    }

    public List<PurchaseDTO> getCustomerPurchases() {

        UserResponseDTO authenticatedUser = userManagementService.getAuthenticatedUser();

        return purchaseRepository.findByCustomerIdOrderByPurchaseDateDesc(authenticatedUser.publicIdentifier()).stream()
                .map(PurchaseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePurchase(Long purchaseId, String status) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("Purchase not found"));
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setStatus(status);
    }

    public PurchaseDTO findById(Long id) {
        return purchaseRepository
                .findById(id)
                .map(PurchaseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }
}
