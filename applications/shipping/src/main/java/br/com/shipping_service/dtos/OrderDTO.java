package br.com.shipping_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        BookDTO book,
        LocalDateTime orderDate,
        Integer quantity,
        BigDecimal totalPrice,
        String status
) {}