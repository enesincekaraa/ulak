package dev.enes.ulak.tenant;

import java.util.UUID;

public interface TenantApi {

    UUID createTenant(String name);

    TenantView getTenant(UUID tenantId);

    boolean exists(UUID tenantId);
}