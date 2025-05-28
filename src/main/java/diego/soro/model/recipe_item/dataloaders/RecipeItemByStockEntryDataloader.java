package diego.soro.model.recipe_item.dataloaders;

import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.recipe_item.RecipeItem;
import diego.soro.model.recipe_item.repository.RecipeItemRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@DgsDataLoader(name = "recipeItemByStockEntryLoader")
public class RecipeItemByStockEntryDataloader implements MappedBatchLoader<Long, List<RecipeItem>> {

    @Autowired
    private RecipeItemRepository recipeItemRepository;

    /**
     * This method will be called once, even if multiple datafetchers use the load() method on the DataLoader.
     * This way reviews can be loaded for all the Shows in a single call instead of per individual Show.
     */
    @Override
    public CompletionStage<Map<Long, List<RecipeItem>>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            // 1. Recupera en batch todos los RecipeItem para esos stockEntryIds
            List<RecipeItem> all = recipeItemRepository.findByStockEntry_IdIn(ids);
            // 2. Agrúpalos por stockEntry.id
            return all.stream()
                    .collect(Collectors.groupingBy(item -> item.getStockEntry().getId()));
        });
    }
}