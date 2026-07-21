package dev.enes.ulak.tenant.web;


import dev.enes.ulak.tenant.TenantApi;
import dev.enes.ulak.tenant.TenantView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
class TenantController {
    private final TenantApi tenantApi;
    public TenantController(TenantApi tenantApi) {
        this.tenantApi = tenantApi;
    }
    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateTenantRequest request){
        UUID id = tenantApi.createTenant(request.name());
        return  ResponseEntity.created(URI.create("/api/v1/tenants/" + id)).
                build();
    }

    @GetMapping("/{tenantId}")
    TenantView get(@PathVariable UUID tenantId) {
        return tenantApi.getTenant(tenantId);
    }

}
