package dev.enes.ulak.ingest.web;

import jakarta.validation.constraints.NotBlank;

public record IngestEventRequest(

        @NotBlank(message = "eventType zorunludur")
        String eventType,

        @NotBlank(message = "payload zorunludur")
        String payload,

        String idempotencyKey
) {
}