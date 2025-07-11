package diego.soro.model2.core.Permission;

import diego.soro.model2.core.Permission.CreatePermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(CreatePermissionDTO dto);

}