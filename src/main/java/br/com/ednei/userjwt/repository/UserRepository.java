package br.com.ednei.userjwt.repository;

import br.com.ednei.userjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    User findByVerificationCode(String verificationCode);

    User findByIdAndVerificationCode(UUID id, String verificationCode);

    boolean existsUserByEmail(String email);
}
