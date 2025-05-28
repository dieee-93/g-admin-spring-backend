// ================================
// RawMaterialDataLoader.java
// ================================
package diego.soro.model.raw_material.dataloaders;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.repository.RawMaterialRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@DgsDataLoader(name = "rawMaterialLoader")
public class RawMaterialDataLoader implements MappedBatchLoader<Long, RawMaterial> {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Override
    public CompletableFuture<Map<Long, RawMaterial>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<RawMaterial> rms = rawMaterialRepository.findAllById(ids);
            Map<Long, RawMaterial> map = new HashMap<>();
            for (RawMaterial rm : rms) {
                map.put(rm.getId(), rm);
            }
            return map;
        });
    }
}
