package br.com.bookstore.service;

import br.com.bookstore.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserManagementService {

    private final RestTemplate restTemplate;
    private final String userManagementHost;

    public UserManagementService(
            @Value("${applications.user-management.host}") String userManagementHost,
            RestTemplate restTemplate
    ) {
        this.userManagementHost = userManagementHost;
        this.restTemplate = restTemplate;
    }

    public UserResponseDTO getUserByEmail(String email) {
        String url = UriComponentsBuilder
                .fromHttpUrl(userManagementHost)
                .path("/users/email/{email}")
                .buildAndExpand(email)
                .toUriString();

        return restTemplate.getForObject(url, UserResponseDTO.class);
    }

    public UserResponseDTO getAuthenticatedUser() {
        String url = UriComponentsBuilder
                .fromHttpUrl(userManagementHost)
                .path("/users/me")
                .toUriString();

        return restTemplate.getForObject(url, UserResponseDTO.class);
    }
}
