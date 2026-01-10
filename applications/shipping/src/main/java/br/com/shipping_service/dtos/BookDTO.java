package br.com.shipping_service.dtos;

import java.math.BigDecimal;

public record BookDTO(
        Long id,
        String title,
        String author,
        String description,
        BigDecimal price,
        String coverImage,
        Integer stock
) {

}