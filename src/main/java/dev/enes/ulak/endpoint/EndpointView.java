package dev.enes.ulak.endpoint;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record EndpointView(
        UUID id,
        UUID tenantId,
        String url,
        String description,
        boolean active,
        Set<String> subscribedEventTypes,
        Instant createdAt
) {
}
