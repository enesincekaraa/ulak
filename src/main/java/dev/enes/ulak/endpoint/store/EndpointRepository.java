package dev.enes.ulak.endpoint.store;

import dev.enes.ulak.endpoint.domain.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EndpointRepository  extends JpaRepository<Endpoint, UUID> {
    List<Endpoint> findByTenantIdAndActiveTrue(UUID tenantId);
}
