package br.com.bookstore.controller;

import br.com.bookstore.dto.BookDto;
import br.com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for browsing and retrieving book information")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Retrieve all available books",
            description = "Fetches a list of all books available in the bookstore.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of books retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(
            summary = "Retrieve a book by its ID",
            description = "Fetches detailed information about a specific book by its unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(
            @Parameter(
                    description = "Unique identifier of the book",
                    example = "1"
            )
            @PathVariable Long bookId
    ) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }
}