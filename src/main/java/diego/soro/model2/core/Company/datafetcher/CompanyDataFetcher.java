package diego.soro.model2.core.Company.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.exception.NotFoundException;
import diego.soro.graphql.generated.types.Branch;
import diego.soro.graphql.generated.types.CreateCompanyInput;
import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.Company.repository.CompanyRepository;
 // <-- Importamos nuestra excepción
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class CompanyDataFetcher {

    private final CompanyRepository companyRepository;

    @DgsQuery
    public List<Company> companies() {
        return companyRepository.findAll();
    }

    @DgsQuery
    public Company companyById(@InputArgument String id) {
        UUID companyID = UUID.fromString(id);
        return companyRepository.findById(companyID)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " not found"));
    }

    @DgsMutation
    public Company createCompany(@InputArgument CreateCompanyInput input) {
        Company company = Company.builder()
                .name(input.getName())
                .taxId(input.getTaxId())
                .email(input.getEmail())
                .phone(input.getPhone())
                .address(input.getAddress())
                .country(input.getCountry())
                .timezone(input.getTimezone())
                .active(true)
                .build();

        return companyRepository.save(company);
    }
    @DgsData(parentType = "Branch", field = "company")
    public Company getCompany(DataFetchingEnvironment dfe) {
        Branch branch = dfe.getSource();
        return branch.getCompany(); // O usar el ID y buscarla si no viene precargada
    }
}
