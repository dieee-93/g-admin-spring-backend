package diego.soro.model.category.dataloaders;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.product.Product;
import diego.soro.model.product.repository.ProductRepository;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.repository.RawMaterialRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
@DgsDataLoader(name = "categoryRawMaterialsLoader")
public class CategoryRawMaterialsDataLoader implements MappedBatchLoader<Long, List<RawMaterial>> {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Override
    public CompletableFuture<Map<Long, List<RawMaterial>>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<RawMaterial> rawMaterials = rawMaterialRepository.findByCategory_IdIn(ids);
            return rawMaterials.stream()
                    .collect(Collectors.groupingBy(m -> m.getCategory().getId()));
        });
    }
}
