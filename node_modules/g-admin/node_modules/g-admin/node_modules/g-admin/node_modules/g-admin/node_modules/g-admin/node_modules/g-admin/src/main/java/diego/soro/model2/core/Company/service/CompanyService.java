package diego.soro.model2.core.Company.service;

import diego.soro.exception.NotFoundException;
import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.Company.CompanyMapper;
import diego.soro.model2.core.Company.CreateCompanyDTO;
import diego.soro.model2.core.Company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Company createCompany(CreateCompanyDTO dto) {
        Company entity = companyMapper.toEntity(dto);
        return companyRepository.save(entity);
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " not found"));
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}