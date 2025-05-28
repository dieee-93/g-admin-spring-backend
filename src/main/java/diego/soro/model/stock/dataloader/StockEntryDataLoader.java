// ================================
// StockEntryDataLoader.java
// ================================
package diego.soro.model.stock.dataloader;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.repository.StockEntryRepository;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@DgsDataLoader(name = "stockEntryLoader")
public class StockEntryDataLoader implements MappedBatchLoader<Long, StockEntry> {

    @Autowired
    private StockEntryRepository stockEntryRepository;

    @Override
    public CompletableFuture<Map<Long, StockEntry>> load(Set<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<StockEntry> stockEntries = stockEntryRepository.findAllById(ids);
            Map<Long, StockEntry> map = new HashMap<>();
            for (StockEntry se : stockEntries) {
                map.put(se.getId(), se);
            }
            return map;
        });
    }
}