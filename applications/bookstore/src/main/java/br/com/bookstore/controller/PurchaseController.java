package br.com.bookstore.controller;

import br.com.bookstore.dto.PurchaseDTO;
import br.com.bookstore.dto.PurchaseRequest;
import br.com.bookstore.service.PurchaseService;
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
@RequestMapping("/purchases")
@Tag(name = "Purchases", description = "Endpoints for managing book purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(
            summary = "Create a new purchase",
            description = "Processes a new book purchase request from a customer.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Purchase request containing book details and customer information",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Purchase completed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PurchaseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid purchase request data",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PurchaseDTO> purchaseBook(
            @RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(purchaseService.purchaseBook(request));
    }

    @Operation(
            summary = "Get customer purchases",
            description = "Retrieves all purchases made by the currently authenticated customer.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of purchases retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PurchaseDTO.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getCustomerPurchases() {
        return ResponseEntity.ok(purchaseService.getCustomerPurchases());
    }

    @Operation(
            summary = "Find a purchase by ID",
            description = "Retrieves a specific purchase by its unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Purchase found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PurchaseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Purchase not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<PurchaseDTO> findById(
            @Parameter(
                    description = "Unique identifier of the purchase",
                    example = "42"
            )
            @PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.findById(id));
    }

}