package br.com.bookstore.dto;

import br.com.bookstore.model.Book;
import java.math.BigDecimal;

public record BookDto(
        Long id,
        String title,
        String author,
        String description,
        BigDecimal price,
        String coverImage,
        Integer stock
) {
    public static BookDto fromEntity(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPrice(),
                book.getCoverImage(),
                book.getStock()
        );
    }

    public static BookDto fromEntityBasic(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                null,
                book.getPrice(),
                book.getCoverImage(),
                null
        );
    }
}