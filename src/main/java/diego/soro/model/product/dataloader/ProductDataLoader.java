// ================================
// StockEntryDataLoader.java
// ================================
package diego.soro.model.product.dataloader;

import com.netflix.graphql.dgs.DgsComponent;

import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.product.Product;
import diego.soro.model.product.repository.ProductRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@DgsDataLoader(name = "productLoader")
public class ProductDataLoader implements MappedBatchLoader<Long, Product> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CompletableFuture<Map<Long, Product>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> products = productRepository.findAllById(ids);
            Map<Long, Product> map = new HashMap<>();
            for (Product p : products) {
                map.put(p.getId(), p);
            }
            return map;
        });
    }
}