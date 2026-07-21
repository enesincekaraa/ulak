package dev.enes.ulak.dispatch.domain;

import dev.enes.ulak.common.UuidV7;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    private UUID id;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "endpoint_id", nullable = false)
    private UUID endpointId;

    @Column(name = "target_url", nullable = false)
    private String targetUrl;

    @Type(JsonType.class)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;

    @Column(name = "attempt_count", nullable = false)
    private int attemptCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Message(UUID eventId, UUID endpointId, String targetUrl, String payload) {
        this.id = UuidV7.generate();
        this.eventId = eventId;
        this.endpointId = endpointId;
        this.targetUrl = targetUrl;
        this.payload = payload;
        this.status = MessageStatus.PENDING;
        this.attemptCount = 0;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void markDelivered(){
        this.status = MessageStatus.DELIVERED;
        this.attemptCount++;
        this.updatedAt = Instant.now();
    }

    public void markFailed() {
        this.status = MessageStatus.FAILED;
        this.attemptCount++;
        this.updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Message message)) return false;
        return id != null && id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
