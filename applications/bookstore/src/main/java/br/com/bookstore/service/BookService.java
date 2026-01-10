package br.com.bookstore.service;

import br.com.bookstore.dto.BookDto;
import br.com.bookstore.exception.ResourceNotFoundException;
import br.com.bookstore.model.Book;
import br.com.bookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final Logger log = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with bookId: " + id));
        return BookDto.fromEntity(book);
    }

    @Transactional
    public BookDto updateBookStock(Long bookId, int quantityChange) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with bookId: " + bookId));
        
        int newStock = book.getStock() + quantityChange;
        if (newStock < 0) {
            throw new IllegalStateException("Insufficient stock");
        }

        log.info("Updating book stock");
        
        book.setStock(newStock);
        Book updatedBook = bookRepository.save(book);
        return BookDto.fromEntity(updatedBook);
    }

    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with bookId: " + bookId));
    }
}
