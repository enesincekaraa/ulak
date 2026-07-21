package dev.enes.ulak.endpoint;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EndpointApi {
    UUID registerEndpoint(UUID tenantId, String url, String description, Set<String> eventTypes);
    List<EndpointView> listEndpoints(UUID tenantId);
    void deactivateEndpoint(UUID endpointId);

    List<EndpointView> findActiveSubscribers(UUID tenantId,String eventType);
}
