package br.com.ednei.userjwt.dto;

import java.util.UUID;

public record TenantResponse(UUID id, String name) {
}
