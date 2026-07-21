package dev.enes.ulak.ingest.store;

import dev.enes.ulak.ingest.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByTenantIdAndIdempotencyKey(UUID tenantId, String idempotencyKey);
}
