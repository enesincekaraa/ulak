package dev.enes.ulak.endpoint.domain;

import dev.enes.ulak.common.UuidV7;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "endpoints")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Endpoint {
    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private String url;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "endpoint_subscriptions",
            joinColumns = @JoinColumn(name = "endpoint_id")
    )
    @Column(name = "event_type", nullable = false)
    private Set<String> subscribedEventTypes=new HashSet<>();


    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    public Endpoint(UUID tenantId, String url, String description) {
        this.id = UuidV7.generate();
        this.tenantId = tenantId;
        this.url = url;
        this.description = description;
        this.active = true;
        this.createdAt = Instant.now();
    }

    public void subscribeTo(String eventType){
        this.subscribedEventTypes.add(eventType);
    }
    public void unsubscribeFrom(String eventType){
        this.subscribedEventTypes.remove(eventType);
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Endpoint endpoint)) return false;
        return id != null && id.equals(endpoint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
