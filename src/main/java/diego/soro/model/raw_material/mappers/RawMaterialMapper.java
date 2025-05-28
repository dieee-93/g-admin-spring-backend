package diego.soro.model.raw_material.mappers;
import diego.soro.graphql.generated.types.RAW_MATERIAL_GQL;
import diego.soro.graphql.generated.types.RAW_MATERIAL_GQLInput;
import diego.soro.model.raw_material.RawMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")

public interface RawMaterialMapper {
    // Mapeo de entidad a tipo GraphQL

    RAW_MATERIAL_GQL entityToGQL(RawMaterial entity);

    // Mapeo de tipo GraphQL a entidad
    @Mapping(target = "category", ignore = true)
    RawMaterial GQLToEntity(RAW_MATERIAL_GQLInput GQLType);

    // Mapeo de listas
    List<RAW_MATERIAL_GQL> entityToGQLList(List<RawMaterial> entities);
    List<RawMaterial>GQLtoEntityList(List<RAW_MATERIAL_GQLInput> GQLTypes);

}