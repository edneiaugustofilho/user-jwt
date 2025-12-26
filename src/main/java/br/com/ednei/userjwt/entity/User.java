package br.com.ednei.userjwt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "tb_users")
public class User extends AuditableEntity implements UserDetails {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_user_tenant",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id"))
    private List<Tenant> tenants;

    public User(UUID id, String name, String email, String password, String verificationCode, boolean enabled) {
        this.setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.enabled = enabled;
    }

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
}
