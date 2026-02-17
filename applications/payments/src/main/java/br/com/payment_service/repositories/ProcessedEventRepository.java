package br.com.payment_service.repositories;

import br.com.payment_service.entities.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO payments.processed_events (idempotency_key, handler_name)
        VALUES (:key, :handler)
        ON CONFLICT (idempotency_key, handler_name) DO NOTHING
        """, nativeQuery = true)
    int insertIfAbsent(@Param("key") UUID key, @Param("handler") String handler);
}