package diego.soro.model2.core.Permission.repository;

import diego.soro.model2.core.Permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByName(String name);
    Set<Permission> findByCodeIn(Set<String> codes);
}
