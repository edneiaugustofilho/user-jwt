package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.entity.Tenant;
import br.com.ednei.userjwt.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantIntegrityValidationService integrityValidationService;

    public TenantService(TenantRepository tenantRepository,
                         TenantIntegrityValidationService integrityValidationService) {
        this.tenantRepository = tenantRepository;
        this.integrityValidationService = integrityValidationService;
    }

    public void save(Tenant tenant) {
        integrityValidationService.execute(tenant);

        tenantRepository.save(tenant);
    }

    public List<Tenant> findAllByUserId(UUID userId) {
        Optional<List<Tenant>> optionalTenants = tenantRepository.findAllByUserId(userId);
        return optionalTenants.orElseGet(ArrayList::new);

    }

}
