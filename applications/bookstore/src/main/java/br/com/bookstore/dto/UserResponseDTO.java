package br.com.bookstore.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String name,
        String email,
        String publicIdentifier,
        LocalDateTime createdAt
) {}
