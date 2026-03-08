package br.com.bookstore.service;

import br.com.bookstore.commons.dto.OrderStatus;
import br.com.bookstore.dto.*;
import br.com.bookstore.model.Book;
import br.com.bookstore.model.EmailTemplate;
import br.com.bookstore.model.Order;
import br.com.bookstore.model.OrderOutbox;
import br.com.bookstore.repository.OrderOutboxRepository;
import br.com.bookstore.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;
    private final BookService bookService;
    private final UserManagementService userManagementService;
    private final EmailQueueService emailQueueService;
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(
            OrderRepository orderRepository,
            OrderOutboxRepository orderOutboxRepository,
            BookService bookService,
            UserManagementService userManagementService,
            EmailQueueService emailQueueService) {
        this.orderRepository = orderRepository;
        this.orderOutboxRepository = orderOutboxRepository;
        this.bookService = bookService;
        this.userManagementService = userManagementService;
        this.emailQueueService = emailQueueService;
    }

    @Transactional
    public OrderDTO orderBook(OrderRequest request) {

        Long bookId = request.bookId();

        log.info("Retrieving book by id");

        Book book = bookService.findBookById(bookId);

        BigDecimal totalPrice = book.getPrice();

        log.info("Retrieving authenticated user");
        UserResponseDTO authenticatedUser = userManagementService.getAuthenticatedUser();

        Order order = new Order();
        order.setBook(book);
        order.setCustomerId(authenticatedUser.customerId());
        order.setQuantity(1);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.ORDER_CREATED.getDescription());

        log.info("Saving order into database");
        Order savedOrder = orderRepository.save(order);

        bookService.updateBookStock(bookId, -1);

        log.info("Saving outbox message to payment");
        OrderOutbox orderOutbox = new OrderOutbox(savedOrder, authenticatedUser.customerId());
        orderOutboxRepository.save(orderOutbox);

        log.info("Send message to email-service");
        EmailMessageRequest emailMessageRequest = new EmailMessageRequest(authenticatedUser.customerId(), order.getBook().getTitle(), EmailTemplate.ORDER_RECEIVED);
        emailQueueService.sendToQueue(emailMessageRequest);

        return OrderDTO.fromEntity(savedOrder);
    }

    public List<OrderDTO> getCustomerOrders() {

        UserResponseDTO authenticatedUser = userManagementService.getAuthenticatedUser();

        return orderRepository.findByCustomerIdOrderByOrderDateDesc(authenticatedUser.customerId()).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOrder(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(status);
    }

    public OrderDTO findById(Long id) {
        return orderRepository
                .findById(id)
                .map(OrderDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
