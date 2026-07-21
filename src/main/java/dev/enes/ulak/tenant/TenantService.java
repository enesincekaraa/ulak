package dev.enes.ulak.tenant;

import dev.enes.ulak.tenant.domain.Tenant;
import dev.enes.ulak.tenant.store.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantService implements TenantApi{
    private final TenantRepository  tenantRepository;
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public UUID createTenant(String name) {
        Tenant tenant = new Tenant(name);
        tenantRepository.save(tenant);
        return tenant.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TenantView getTenant(UUID tenantId) {
        return tenantRepository.findById(tenantId).map(
                t-> new TenantView(t.getId(),t.getName(),t.getCreatedAt())
        )
                .orElseThrow(() -> new IllegalArgumentException("Tenant bulunamadı: " + tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(UUID tenantId) {
        return tenantRepository.existsById(tenantId);
    }
}
