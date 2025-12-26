package br.com.ednei.userjwt.repository;

import br.com.ednei.userjwt.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    @Query("SELECT t FROM Tenant t JOIN t.users u WHERE u.id = :userId AND t.status = 'ACTIVE'")
    Optional<List<Tenant>> findAllByUserId(UUID userId);
}
