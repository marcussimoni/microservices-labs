package br.com.shipping_service.dtos;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String name,
        String email,
        String city,
        String state,
        String publicIdentifier,
        LocalDateTime createdAt
) {}