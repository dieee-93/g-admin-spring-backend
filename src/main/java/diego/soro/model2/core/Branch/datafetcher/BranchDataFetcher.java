package diego.soro.model2.core.Branch.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.GAdminErrorType;
import diego.soro.GAdminException;
import diego.soro.graphql.generated.types.CreateBranchInput;
import diego.soro.model2.core.Branch.Branch;
import diego.soro.model2.core.Branch.CreateBranchDTO;
import diego.soro.model2.core.Branch.service.BranchService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class BranchDataFetcher {

    private final BranchService branchService;

    @DgsMutation
    public Branch createBranch(@InputArgument CreateBranchInput input) {
        // Validaciones ad hoc
        if (input.getName() == null || input.getName().isBlank()) {
            throw new GAdminException("Branch name is required.", GAdminErrorType.VALIDATION_FAILED);
        }
        if (input.getCode() == null || input.getCode().isBlank()) {
            throw new GAdminException("Branch code is required.", GAdminErrorType.VALIDATION_FAILED);
        }
        // Mapeo manual a DTO
        CreateBranchDTO dto = CreateBranchDTO.builder()
                .name(input.getName())
                .code(input.getCode())
                .address(input.getAddress())
                .phone(input.getPhone())
                .email(input.getEmail())
                .companyId(input.getCompanyId())
                .isMain(input.getIsMain() != null && input.getIsMain()) // default false
                .build();

        // Delegar al servicio
        return branchService.createBranch(dto);
    }

    @DgsQuery
    public List<Branch> getBranches() {
        return branchService.getAllBranchesWithCompany();
    }

}