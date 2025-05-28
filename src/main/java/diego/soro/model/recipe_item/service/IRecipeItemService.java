package diego.soro.model.recipe_item.service;


import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.recipe_item.RecipeItem;

import java.util.List;

public interface IRecipeItemService {

    List<RecipeItem> findRecipeItems();

    RecipeItem findById(Long id);

    void removeRecipeItem(Long id);

    void saveRecipeItem(RecipeItem recipeItem);

    void updateRecipeItem(RecipeItem recipeItem);
}
