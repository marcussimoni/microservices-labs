package br.com.email_sender.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseDTO(
        Long id,
        String book,
        LocalDateTime purchaseDate,
        Integer quantity,
        BigDecimal totalPrice
) {}