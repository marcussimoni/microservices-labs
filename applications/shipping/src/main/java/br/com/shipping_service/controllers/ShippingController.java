package br.com.shipping_service.controllers;

import br.com.shipping_service.entities.Shipping;
import br.com.shipping_service.services.ShippingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("shipping")
@Tag(name = "Shipping", description = "Endpoints for managing shipping information")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Operation(
            summary = "Find shipping by ID",
            description = "Returns a shipping record for the given internal shipping ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Shipping found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Shipping.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Shipping not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/id/{id}")
    public Shipping getById(
            @Parameter(
                    description = "Internal ID of the shipping record",
                    example = "1"
            )
            @PathVariable Long id
    ) {
        return shippingService.findById(id);
    }

    @Operation(
            summary = "Find shipping by public identifier",
            description = "Retrieves all shipping records associated with a given public identifier (e.g., order tracking code).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of shipping records retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Shipping.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No shipping records found for the provided public identifier",
                            content = @Content
                    )
            }
    )
    @GetMapping("/public-identifier/{publicIdentifier}")
    public List<Shipping> getByPublicIdentifier(
            @Parameter(
                    description = "Public tracking or reference identifier for the shipment",
                    example = "TRACK-123456789"
            )
            @PathVariable String publicIdentifier
    ) {
        return shippingService.findByPublicIdentifier(publicIdentifier);
    }
}
