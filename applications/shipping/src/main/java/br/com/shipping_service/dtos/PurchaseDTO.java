package br.com.shipping_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseDTO(
        Long id,
        BookDTO book,
        LocalDateTime purchaseDate,
        Integer quantity,
        BigDecimal totalPrice,
        String status
) {}