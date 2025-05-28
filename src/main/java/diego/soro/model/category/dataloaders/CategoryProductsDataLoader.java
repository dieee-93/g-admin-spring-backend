package diego.soro.model.category.dataloaders;

import com.netflix.graphql.dgs.*;
import diego.soro.model.product.Product;
import diego.soro.model.product.repository.ProductRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
@DgsDataLoader(name = "categoryProductsLoader")
public class CategoryProductsDataLoader implements MappedBatchLoader<Long, List<Product>> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CompletableFuture<Map<Long, List<Product>>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> prods = productRepository.findByCategory_IdIn(ids);
            return prods.stream()
                    .collect(Collectors.groupingBy(p -> p.getCategory().getId()));
        });
    }
}