// ================================
// CategoryDataLoader.java
// ================================
package diego.soro.model.category.dataloaders;

import com.netflix.graphql.dgs.DgsComponent;
import diego.soro.model.category.Category;
import diego.soro.model.category.repository.CategoryRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class CategoryDataLoader implements MappedBatchLoader<Long, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CompletableFuture<Map<Long, Category>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Category> cats = categoryRepository.findAllById(ids);
            Map<Long, Category> map = new HashMap<>();
            for (Category c : cats) {
                map.put(c.getId(), c);
            }
            return map;
        });
    }
}

