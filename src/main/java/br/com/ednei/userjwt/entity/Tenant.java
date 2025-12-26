package br.com.ednei.userjwt.entity;

import br.com.ednei.userjwt.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tenants")
public class Tenant extends AuditableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TenantStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_user_tenant",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
