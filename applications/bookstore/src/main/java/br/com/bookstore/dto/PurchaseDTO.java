package br.com.bookstore.dto;

import br.com.bookstore.model.Book;
import br.com.bookstore.model.Purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseDTO(
        Long id,
        BookDto book,
        LocalDateTime purchaseDate,
        Integer quantity,
        BigDecimal totalPrice,
        String status
) {
    public static PurchaseDTO fromEntity(Purchase purchase) {
        return new PurchaseDTO(
                purchase.getId(),
                BookDto.fromEntityBasic(purchase.getBook()),
                purchase.getPurchaseDate(),
                purchase.getQuantity(),
                purchase.getTotalPrice(),
                purchase.getStatus()
        );
    }

    public Purchase toEntity() {
        Purchase purchase = new Purchase();
        purchase.setId(this.id);
        purchase.setBook(this.book != null ?
                new Book(
                        this.book.id(),
                        this.book.title(),
                        this.book.author(),
                        this.book.description(),
                        this.book.price(),
                        this.book.coverImage(),
                        this.book.stock()
                ) : null);
        purchase.setPurchaseDate(this.purchaseDate);
        purchase.setQuantity(this.quantity);
        purchase.setTotalPrice(this.totalPrice);
        return purchase;
    }
}