package dev.enes.ulak.tenant;

import java.time.Instant;
import java.util.UUID;

public record TenantView(
        UUID id,
        String name,
        Instant createdAt
) {
}
