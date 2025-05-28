package diego.soro.model.recipe_item.service;


import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.repository.RawMaterialRepository;
import diego.soro.model.recipe_item.RecipeItem;
import diego.soro.model.recipe_item.repository.RecipeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeItemService implements IRecipeItemService {
    @Autowired
    private RecipeItemRepository repository;

    @Override
    public List<RecipeItem> findRecipeItems() {
        return repository.findAll();
    }

    @Override
    public RecipeItem findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void removeRecipeItem(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void saveRecipeItem(RecipeItem recipeItem) {
        repository.save(recipeItem);
    }


    @Override
    public void updateRecipeItem(RecipeItem recipeItem) {
        repository.save(recipeItem);
    }
}
