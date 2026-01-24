package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.entity.Tenant;
import br.com.ednei.userjwt.entity.User;
import br.com.ednei.userjwt.entity.UserRole;
import br.com.ednei.userjwt.repository.TenantRepository;
import br.com.ednei.userjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserCreateService {

    @Value("${app.company.admin-user.email}")
    private String adminUserEmail;

    @Value("${app.company.admin-user.password}")
    private String adminUserPassword;

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserCreateService(UserRepository userRepository,
                                  TenantRepository tenantRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute() {
        if (userRepository.existsUserByEmail(adminUserEmail)) {
            User user = userRepository.findByEmail(adminUserEmail);
            user.setRole(UserRole.ADMIN);
            user.setTenants(getTenatns());
            user.setPassword(passwordEncoder.encode(adminUserPassword));

            userRepository.save(user);
        } else {
            User user = User.builder().
                    name("Administrador").
                    email(adminUserEmail).
                    password(passwordEncoder.encode(adminUserPassword)).
                    enabled(true).
                    verificationCode("").
                    role(UserRole.ADMIN).
                    tenants(getTenatns()).
                    build();

            userRepository.save(user);
        }
    }

    private List<Tenant> getTenatns() {
        return tenantRepository.findAllActiveTenants().orElse(List.of());
    }

}
