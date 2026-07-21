package dev.enes.ulak.tenant.store;

import dev.enes.ulak.tenant.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    UUID id(UUID id);
}
