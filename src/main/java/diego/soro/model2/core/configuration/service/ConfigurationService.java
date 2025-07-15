package diego.soro.model2.core.configuration.service;

import diego.soro.model2.core.configuration.CompanyConfiguration;
import diego.soro.model2.core.configuration.repository.CompanyConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationService {
    
    private final CompanyConfigurationRepository repository;
    
    public Optional<CompanyConfiguration> getByCompanyId(Long companyId) {
        return repository.findByCompanyId(companyId);
    }
    
    public CompanyConfiguration save(CompanyConfiguration configuration) {
        return repository.save(configuration);
    }
}