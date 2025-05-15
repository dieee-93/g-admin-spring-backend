package diego.soro.model.recipe_item.mappers;//package diego.soro.model.mappers;

import diego.soro.graphql.generated.types.RECIPE_ITEM_GQL;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.recipe_item.RecipeItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RecipeItemMapper {
    // Mapeo de entidad a tipo GraphQL
    RECIPE_ITEM_GQL entityToGQL(RecipeItem entity);

    // Mapeo de tipo GraphQL a entidad
    RecipeItem GQLToEntity(RECIPE_ITEM_GQL GQLType);

    // Mapeo de listas
    List<RECIPE_ITEM_GQL> entityToGQLList(List<RecipeItem> entities);
    List<RecipeItem>GQLtoEntityList(List<RECIPE_ITEM_GQL> GQLTypes);


}