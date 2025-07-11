package diego.soro.model2.core.Permission.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.graphql.generated.types.CreatePermissionInput;
import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Permission.CreatePermissionDTO;
import diego.soro.model2.core.Permission.service.PermissionService;
import diego.soro.model2.core.Role.Role;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class PermissionDataFetcher {

    private final PermissionService permissionService;

    @DgsQuery
    public List<Permission> permissions() {
        return permissionService.findAll();
    }

    @DgsQuery
    public Permission permissionById(@InputArgument String id) {
        return permissionService.findByIdOrThrow(UUID.fromString(id));
    }

    @DgsMutation
    public Permission createPermission(@InputArgument CreatePermissionInput input) {
        // Map manually Input → DTO
        CreatePermissionDTO dto = CreatePermissionDTO.builder()
                .name(input.getName())
                .description(input.getDescription())
                .code(input.getCode())
                .isSystem(input.getIsSystem() != null ? input.getIsSystem() : false)
                .build();
        return permissionService.createPermission(dto);
    }
}