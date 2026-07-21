package dev.enes.ulak.endpoint.web;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterEndpointRequest(

        @NotBlank(message = "url zorunludur")
        @Size(max = 2048, message = "url en fazla 2048 karakter olabilir")
        String url,

        @Size(max = 500, message = "description en fazla 500 karakter olabilir")
        String description,

        @NotEmpty(message = "en az bir event type gereklidir")
        Set<String> eventTypes
) {
}