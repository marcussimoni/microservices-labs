package br.com.payment_service.services;

import br.com.payment_service.repositories.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedEventService {

    private final ProcessedEventRepository repository;

    @Transactional
    public boolean eventProcessed(UUID key, String handler) {
        int rowsAffected = repository.insertIfAbsent(key, handler);
        
        if (rowsAffected == 0) {
            log.info("Duplicate event detected: {} for handler: {}. Skipping.", key, handler);
            return true;
        }
        
        return false;
    }
}