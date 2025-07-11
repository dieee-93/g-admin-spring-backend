package diego.soro.model2.core.Branch.repository;

import diego.soro.model2.core.Branch.Branch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {
    List<Branch> findByCompanyId(UUID companyId);
    @EntityGraph(attributePaths = "company")
    List<Branch> findAllWithCompany();
}

