package br.com.payment_service.controllers;

import br.com.payment_service.dtos.PaymentGatewayResponse;
import br.com.payment_service.dtos.PaymentResponseDTO;
import br.com.payment_service.entities.Payments;
import br.com.payment_service.services.PaymentsService;
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
@RequestMapping("/payments")
@Tag(name = "Payments", description = "Endpoints for managing payments")
public class PaymentsController {

    private final PaymentsService service;

    public PaymentsController(PaymentsService service) {
        this.service = service;
    }

    @Operation(
        summary = "Create a new payment",
        description = "Registers a new payment with its amount, status, and associated purchase ID.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Payment successfully created",
                content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = @Content
            )
        }
    )

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findByPurchaseIds(
            @Parameter(
                description = "List of purchase IDs to filter payments by",
                required = true,
                example = "1,2,3"
            )
            @RequestParam List<Long> purchaseIds) {
        List<Payments> payments = service.findByPurchaseIds(purchaseIds);
        return ResponseEntity.ok(payments.stream().map(PaymentResponseDTO::fromEntity).toList());
    }

    @GetMapping("all")
    public List<PaymentGatewayResponse> findAll() {
        return service.findPayments();
    }

}