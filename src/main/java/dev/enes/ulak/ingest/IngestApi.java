package dev.enes.ulak.ingest;

import java.util.UUID;

public interface IngestApi {
    UUID ingest(UUID tenantId,String eventType,String payload,String idempotencyKey);
}
