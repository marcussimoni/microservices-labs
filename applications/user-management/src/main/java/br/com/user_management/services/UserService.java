package br.com.user_management.services;

import br.com.user_management.entities.User;
import br.com.user_management.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "users", key = "#email", unless = "#result == null")
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User findAuthenticatedUser() {
        Iterator<User> iterator = repository.findAll().iterator();
        List<User> users = new ArrayList<>();
        iterator.forEachRemaining(users::add);
        return users
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Cacheable(value = "users", key = "#id", unless = "#result == null")
    public User findById(String id) {
        return repository.findByPublicIdentifier(id);
    }
}