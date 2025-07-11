package diego.soro.model2.core.Branch.service;

import diego.soro.GAdminErrorType;
import diego.soro.GAdminException;
import diego.soro.model2.core.Branch.Branch;
import diego.soro.model2.core.Branch.BranchMapper;
import diego.soro.model2.core.Branch.CreateBranchDTO;
import diego.soro.model2.core.Branch.repository.BranchRepository;
import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.Company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapper branchMapper;

    public List<Branch> getAllBranchesWithCompany() {
        return branchRepository.findAllWithCompany();
    }

    public Branch createBranch(CreateBranchDTO dto) {
        // Validación y carga de la empresa
        Company company = companyRepository.findById(UUID.fromString(dto.getCompanyId()))
                .orElseThrow(() -> new GAdminException("Company not found", GAdminErrorType.NOT_FOUND));

        // Mapeo
        Branch branch = branchMapper.toEntity(dto);
        branch.setCompany(company);
        branch.setActive(true); // Campo de BaseEntity

        return branchRepository.save(branch);
    }

}