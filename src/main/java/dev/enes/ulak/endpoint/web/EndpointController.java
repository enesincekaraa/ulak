package dev.enes.ulak.endpoint.web;


import dev.enes.ulak.endpoint.EndpointApi;
import dev.enes.ulak.endpoint.EndpointView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/endpoints")
class EndpointController {

    private final EndpointApi endpointApi;

    public EndpointController(EndpointApi endpointApi) {
        this.endpointApi = endpointApi;
    }

    @PostMapping
    ResponseEntity<Void> register(
            @PathVariable UUID tenantId,
             @Valid @RequestBody RegisterEndpointRequest request
            ){
        UUID id = endpointApi.registerEndpoint(
                tenantId, request.url(), request.description(), request.eventTypes());

        return ResponseEntity
                .created(URI.create("/api/v1/tenants/" + tenantId + "/endpoints/" + id))
                .build();
    }

    @GetMapping
    List<EndpointView> list(@PathVariable UUID tenantId) {
        return endpointApi.listEndpoints(tenantId);
    }

    @DeleteMapping("/{endpointId}")
    ResponseEntity<Void> deactivate(
            @PathVariable UUID tenantId,
            @PathVariable UUID endpointId) {

        endpointApi.deactivateEndpoint(endpointId);
        return ResponseEntity.noContent().build();
    }

}
