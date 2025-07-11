package diego.soro.model2.core.Role;

import diego.soro.model2.core.Role.CreateRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true) // Se setean a mano en el service
    @Mapping(target = "parentRole", ignore = true)  // También se resuelve manualmente
    @Mapping(target = "users", ignore = true)       // Invertida, no se toca en creación
    Role toEntity(CreateRoleDTO dto);
}