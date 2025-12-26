package br.com.ednei.userjwt.gateway.restful;

import br.com.ednei.userjwt.dto.TenantResponse;
import br.com.ednei.userjwt.entity.Tenant;
import br.com.ednei.userjwt.service.TenantService;
import br.com.ednei.userjwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tenant")
public class TenantResource {

    private final TenantService tenantService;
    private final UserService userService;

    public TenantResource(TenantService tenantService,
                          UserService userService) {
        this.tenantService = tenantService;
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TenantResponse>> findAll(@RequestParam(value = "userId") UUID userId) {
        boolean userExists = userService.existsByid(userId);
        if (!userExists) {
            return ResponseEntity.notFound().build();
        }

        List<Tenant> tenants = tenantService.findAllByUserId(userId);
        if (tenants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TenantResponse> tenantsOfUserResponse = tenants.stream()
                .map(tenant -> new TenantResponse(tenant.getId(), tenant.getName()))
                .toList();

        return ResponseEntity.ok(tenantsOfUserResponse);
    }

}
