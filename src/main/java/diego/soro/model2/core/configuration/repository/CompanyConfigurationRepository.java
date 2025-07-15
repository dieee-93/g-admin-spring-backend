package diego.soro.model2.core.configuration.repository;

import diego.soro.model2.core.configuration.CompanyConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyConfigurationRepository extends JpaRepository<CompanyConfiguration, Long> {
    
    Optional<CompanyConfiguration> findByCompanyId(Long companyId);
}