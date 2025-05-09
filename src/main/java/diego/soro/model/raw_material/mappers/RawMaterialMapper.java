package diego.soro.model.raw_material.mappers;//package diego.soro.model.mappers;
import diego.soro.graphql.generated.types.RawMaterialGQL;
import diego.soro.graphql.generated.types.RawMaterialType;
import diego.soro.model.raw_material.RawMaterial;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RawMaterialMapper {
    // Mapeo de entidad a tipo GraphQL
    RawMaterialGQL entityToGQL(RawMaterial entity);

    // Mapeo de tipo GraphQL a entidad
    RawMaterial GQLToEntity(RawMaterialGQL GQLType);

    // Mapeo de listas
    List<RawMaterialGQL> entityToGQLList(List<RawMaterial> entities);
    List<RawMaterial>GQLtoEntityList(List<RawMaterialGQL> GQLTypes);

}