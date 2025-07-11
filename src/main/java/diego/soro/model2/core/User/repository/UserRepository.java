package diego.soro.model2.core.User.repository;

import diego.soro.model2.core.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndTenantId(String username, UUID tenantId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}