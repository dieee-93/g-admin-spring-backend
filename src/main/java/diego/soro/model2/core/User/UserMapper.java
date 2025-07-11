package diego.soro.model2.core.User;

import diego.soro.model2.core.Branch.Branch;
import diego.soro.model2.core.Role.Role;
import diego.soro.model2.core.User.User;
import diego.soro.model2.core.User.CreateUserDTO;
import diego.soro.graphql.generated.types.UserGQL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // DTO → Entity (sin relaciones aún: se inyectan manualmente en el service)
    @Mapping(target = "branches", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "keycloakId", ignore = true)
    User toEntity(CreateUserDTO dto);

    // Entity → GraphQL Type
    @Mapping(target = "id", source = "id")
    @Mapping(target = "branchIds", source = "branches", qualifiedByName = "mapBranchesToIds")
    @Mapping(target = "roleIds", source = "roles", qualifiedByName = "mapRolesToIds")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().toString())")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdatedAt().toString())")
    UserGQL toGraphQL(User entity);

    @Named("mapBranchesToIds")
    static List<String> mapBranchesToIds(Set<? extends Branch> branches) {
        return branches.stream().map(b -> b.getId().toString()).toList();
    }

    @Named("mapRolesToIds")
    static List<String> mapRolesToIds(Set<? extends Role> roles) {
        return roles.stream().map(r -> r.getId().toString()).toList();
    }
}