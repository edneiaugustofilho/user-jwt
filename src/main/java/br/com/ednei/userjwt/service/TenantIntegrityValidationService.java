package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.entity.Tenant;
import org.springframework.stereotype.Service;

@Service
public class TenantIntegrityValidationService {

    public void execute(Tenant tenant) {
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant cannot be null");
        }

        if (tenant.getName() == null || tenant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tenant name cannot be null or empty");
        } else if (tenant.getName().length() < 5) {
            throw new IllegalArgumentException("Tenant name must have at least 5 characters");
        }

        if (tenant.getStatus() == null) {
            throw new IllegalArgumentException("Tenant status cannot be null");
        }
    }

}
