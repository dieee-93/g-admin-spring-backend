package diego.soro.model.category.dataloaders;

import com.netflix.graphql.dgs.*;
import diego.soro.model.category.Category;
import diego.soro.model.category.repository.CategoryRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@DgsDataLoader(name = "categoryParentLoader")
public class CategoryParentDataLoader implements MappedBatchLoader<Long, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CompletableFuture<Map<Long, Category>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Category> parents = categoryRepository.findAllById(ids);
            Map<Long, Category> map = new HashMap<>();
            for (Category c : parents) {
                map.put(c.getId(), c);
            }
            return map;
        });
    }
}