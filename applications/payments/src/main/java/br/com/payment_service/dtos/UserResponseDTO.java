package br.com.payment_service.dtos;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String name,
        String email,
        String publicIdentifier,
        LocalDateTime createdAt
) {}
