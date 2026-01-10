package br.com.user_management.dto;

import br.com.user_management.entities.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        String name,
        String email,
        String state,
        String city,
        String publicIdentifier,
        LocalDateTime createdAt
) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
                user.getName(),
                user.getEmail(),
                user.getState(),
                user.getCity(),
                user.getPublicIdentifier(),
                user.getCreatedAt()
        );
    }
}