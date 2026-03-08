package br.com.email_sender.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        String book,
        LocalDateTime orderDate,
        Integer quantity,
        BigDecimal totalPrice
) {}