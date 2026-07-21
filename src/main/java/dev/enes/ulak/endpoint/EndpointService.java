package dev.enes.ulak.endpoint;

import dev.enes.ulak.endpoint.domain.Endpoint;
import dev.enes.ulak.endpoint.store.EndpointRepository;
import dev.enes.ulak.tenant.TenantApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
class EndpointService implements EndpointApi {
    private final EndpointRepository endpointRepository;
    private final TenantApi tenantApi;
    private static final Logger log = LoggerFactory.getLogger(EndpointService.class);

    public EndpointService(EndpointRepository endpointRepository, TenantApi tenantApi) {
        this.endpointRepository = endpointRepository;

        this.tenantApi = tenantApi;
    }

    @Override
    @Transactional
    public UUID registerEndpoint(UUID tenantId, String url, String description, Set<String> eventTypes) {
        if (!tenantApi.exists(tenantId)) {
            log.warn("Bilinmeyen tenant'a endpoint kaydı denendi: tenantId={}", tenantId);
            throw new IllegalArgumentException("Tenant bulunamadı: " + tenantId);
        }

        Endpoint endpoint = new Endpoint(tenantId, url, description);
        eventTypes.forEach(endpoint::subscribeTo);
        endpointRepository.save(endpoint);
        log.info("Endpoint kaydedildi: tenantId={}, endpointId={}, eventTypes={}",
                tenantId, endpoint.getId(), eventTypes);

        return endpoint.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EndpointView> listEndpoints(UUID tenantId) {
        return endpointRepository.findByTenantIdAndActiveTrue(tenantId)
                .stream()
                .map(this::toView)
                .toList();
    }

    @Override
    @Transactional
    public void deactivateEndpoint(UUID endpointId) {
        Endpoint endpoint = endpointRepository.findById(endpointId)
                .orElseThrow(() -> new IllegalArgumentException("Endpoint bulunamadı: " + endpointId));
        endpoint.deactivate();
    }

    private EndpointView toView(Endpoint endpoint) {
        return new EndpointView(
                endpoint.getId(),
                endpoint.getTenantId(),
                endpoint.getUrl(),
                endpoint.getDescription(),
                endpoint.isActive(),
                new HashSet<>(endpoint.getSubscribedEventTypes()),
                endpoint.getCreatedAt()
        );
    }
}
