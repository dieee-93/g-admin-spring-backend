package diego.soro.model2.core.Role.repository;

import diego.soro.model2.core.Role.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByNameAndTenantId(String name, UUID tenantId);

    @EntityGraph(attributePaths = {"permissions", "parentRole"})
    @NonNull
    List<Role> findAll();

    @EntityGraph(attributePaths = {"permissions", "parentRole"})
    @NonNull
    Optional<Role> findById(@NonNull UUID id);
}