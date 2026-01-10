package br.com.user_management.controllers;

import br.com.user_management.dto.UserResponseDto;
import br.com.user_management.entities.User;
import br.com.user_management.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints for managing and retrieving user information")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Find user by id",
            description = "Retrieves a user based on their email address."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @Parameter(description = "User id", example = "3292f0cb-cc5d-480e-b5b7-7619e676c8f5")
            @PathVariable String id) {
        User user = service.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponseDto.fromEntity(user));
    }

    @Operation(
            summary = "Find user by email",
            description = "Retrieves a user based on their email address."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(
            @Parameter(description = "User email address", example = "admin@example.com")
            @PathVariable String email) {
        User user = service.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponseDto.fromEntity(user));
    }

    @Operation(
            summary = "Get authenticated user",
            description = "Retrieves details of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated user details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No authenticated user found")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> findAuthenticated() {
        User user = service.findAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponseDto.fromEntity(user));
    }
}