package br.com.bookstore.dto;

import br.com.bookstore.model.Book;
import br.com.bookstore.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        BookDto book,
        LocalDateTime orderDate,
        Integer quantity,
        BigDecimal totalPrice,
        String status
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                BookDto.fromEntityBasic(order.getBook()),
                order.getOrderDate(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus()
        );
    }

    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setBook(this.book != null ?
                new Book(
                        this.book.id(),
                        this.book.title(),
                        this.book.author(),
                        this.book.description(),
                        this.book.price(),
                        this.book.coverImage(),
                        this.book.stock()
                ) : null);
        order.setOrderDate(this.orderDate);
        order.setQuantity(this.quantity);
        order.setTotalPrice(this.totalPrice);
        return order;
    }
}