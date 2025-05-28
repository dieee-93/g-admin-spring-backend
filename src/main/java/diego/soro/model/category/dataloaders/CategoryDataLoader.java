// ================================
// CategoryDataLoader.java
// ================================
package diego.soro.model.category.dataloaders;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.category.Category;
import diego.soro.model.category.repository.CategoryRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;


@DgsComponent
@DgsDataLoader(name = "categoryLoader")
public class CategoryDataLoader implements MappedBatchLoader<Long, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CompletableFuture<Map<Long, Category>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> categoryRepository.findAllById(new ArrayList<>(ids)).stream()
                .collect(HashMap::new, (m, c) -> m.put(c.getId(), c), HashMap::putAll));
    }
}

