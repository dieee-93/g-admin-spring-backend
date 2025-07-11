package diego.soro.model2.core.Role.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.graphql.generated.types.CreateRoleInput;
import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Role.Role;
import diego.soro.model2.core.Role.CreateRoleDTO;
import diego.soro.model2.core.Role.service.RoleService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
@RequiredArgsConstructor
public class RoleDataFetcher {

    private final RoleService roleService;

    @DgsQuery
    public List<Role> roles() {
        return roleService.findAll();
    }

    @DgsQuery
    public Role roleById(@InputArgument String id) {
        return roleService.findByIdOrThrow(UUID.fromString(id));
    }

    @DgsData(parentType = "Role", field = "permissions")
    public Set<Permission> getPermissions(DataFetchingEnvironment dfe) {
        Role role = dfe.getSource();
        return role.getPermissions(); // Ya viene cargado con @EntityGraph
    }

    @DgsData(parentType = "Role", field = "parentRole")
    public Role getParentRole(DataFetchingEnvironment dfe) {
        Role role = dfe.getSource();
        return role.getParentRole(); // Precargado también
    }


    @DgsMutation
    public Role createRole(@InputArgument CreateRoleInput input) {
        // Map manually Input → DTO
        CreateRoleDTO dto = CreateRoleDTO.builder()
                .name(input.getName())
                .description(input.getDescription())
                .isSystem(input.getIsSystem() != null ? input.getIsSystem() : false)
                .parentRoleId(input.getParentRoleId() != null ? UUID.fromString(input.getParentRoleId()) : null)
                .permissionIds(input.getPermissionIds() != null
                        ? input.getPermissionIds().stream().map(UUID::fromString).collect(Collectors.toSet())
                        : Set.of())
                .build();

        return roleService.createRole(dto);
    }

    // Resolver de relaciones si hiciera falta (permissions o parentRole) iría acá si no usamos @EntityGraph
}
