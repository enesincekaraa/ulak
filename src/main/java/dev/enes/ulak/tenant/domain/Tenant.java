package dev.enes.ulak.tenant.domain;


import dev.enes.ulak.common.UuidV7;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tenant {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;


    public Tenant(String name) {
        this.id= UuidV7.generate();
        this.name = name;
        this.createdAt = Instant.now();
    }
    public void rename(String newName) {
        this.name = newName;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Tenant tenant)) return false;
        return id != null && id.equals(tenant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
