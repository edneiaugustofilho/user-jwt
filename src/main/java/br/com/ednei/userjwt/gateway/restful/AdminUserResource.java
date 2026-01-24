package br.com.ednei.userjwt.gateway.restful;

import br.com.ednei.userjwt.service.AdminUserCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserResource {

    private final AdminUserCreateService adminUserCreateService;

    public AdminUserResource(AdminUserCreateService adminUserCreateService) {
        this.adminUserCreateService = adminUserCreateService;
    }

    @GetMapping("/public/create-adm")
    ResponseEntity<?> createAdmin() {
        adminUserCreateService.execute();

        return ResponseEntity.ok("");
    }

}
