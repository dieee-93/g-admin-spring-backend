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

@DgsDataLoader(name = "recipeItemByProductLoader")
public class RecipeItemByProductDataLoader implements MappedBatchLoader<Long, List<RecipeItem>> {
    @Autowired
    private RecipeItemRepository recipeItemRepo;

    @Override
    public CompletionStage<Map<Long, List<RecipeItem>>> load(Set<Long> productIds) {
        return CompletableFuture.supplyAsync(() -> {
            List<RecipeItem> all = recipeItemRepo.findByProduct_IdIn(productIds);
            return all.stream()
                    .collect(Collectors.groupingBy(item -> item.getProduct().getId()));
        });
    }
}
