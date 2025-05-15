package diego.soro.model.raw_material.mappers;
import diego.soro.graphql.generated.types.RAW_MATERIAL_GQL;
import diego.soro.model.raw_material.RawMaterial;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RawMaterialMapper {
    // Mapeo de entidad a tipo GraphQL
    RAW_MATERIAL_GQL entityToGQL(RawMaterial entity);

    // Mapeo de tipo GraphQL a entidad
    RawMaterial GQLToEntity(RAW_MATERIAL_GQL GQLType);

    // Mapeo de listas
    List<RAW_MATERIAL_GQL> entityToGQLList(List<RawMaterial> entities);
    List<RawMaterial>GQLtoEntityList(List<RAW_MATERIAL_GQL> GQLTypes);

}