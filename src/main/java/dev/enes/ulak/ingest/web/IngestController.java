package dev.enes.ulak.ingest.web;


import dev.enes.ulak.ingest.IngestApi;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/events")
class IngestController {
    private final IngestApi ingestApi;

    public IngestController(IngestApi ingestApi) {
        this.ingestApi = ingestApi;
    }

    @PostMapping
    ResponseEntity<Void> ingest(
            @PathVariable UUID tenantId,
            @Valid @RequestBody IngestEventRequest request
            ){
        UUID eventId =ingestApi.ingest(
                tenantId, request.eventType(), request.payload(), request.idempotencyKey());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("X-Event-Id", eventId.toString())
                .build();
    }


}
