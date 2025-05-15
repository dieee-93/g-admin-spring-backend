package diego.soro.model.category.mappers;
import diego.soro.graphql.generated.types.CATEGORY_GQL;
import diego.soro.graphql.generated.types.CATEGORY_GQLInput;
import diego.soro.graphql.generated.types.RAW_MATERIAL_GQL;
import diego.soro.model.category.Category;
import diego.soro.model.raw_material.RawMaterial;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // Mapeo de entidad a tipo GraphQL
    CATEGORY_GQL entityToGQL(Category entity);

    // Mapeo de tipo GraphQL a entidad
    Category GQLToEntity(CATEGORY_GQLInput GQLType);

    // Mapeo de listas
    List<CATEGORY_GQL> entityToGQLList(List<Category> entities);
    List<Category>GQLtoEntityList(List<CATEGORY_GQLInput> GQLTypes);

}