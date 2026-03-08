package br.com.bookstore.controller;

import br.com.bookstore.dto.OrderDTO;
import br.com.bookstore.dto.OrderRequest;
import br.com.bookstore.service.OrderService;
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
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Endpoints for managing book orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Create a new order",
            description = "Processes a new book order request from a customer.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order request containing book details and customer information",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order completed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid order request data",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<OrderDTO> orderBook(
            @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.orderBook(request));
    }

    @Operation(
            summary = "Get customer orders",
            description = "Retrieves all orders made by the currently authenticated customer.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of orders retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getCustomerOrders() {
        return ResponseEntity.ok(orderService.getCustomerOrders());
    }

    @Operation(
            summary = "Find an order by ID",
            description = "Retrieves a specific order by its unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDTO> findById(
            @Parameter(
                    description = "Unique identifier of the order",
                    example = "42"
            )
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

}