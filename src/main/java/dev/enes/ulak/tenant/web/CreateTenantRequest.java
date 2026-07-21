package dev.enes.ulak.tenant.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTenantRequest(

        @NotBlank(message = "name zorunludur")
        @Size(max = 200, message = "name en fazla 200 karakter olabilir")
        String name
) {
}