package diego.soro.model.stock.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.StockSnapshot;
import diego.soro.model.stock.dataloader.StockEntryDataLoader;
import diego.soro.model.stock.service.StockService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class StockDataFetcher {
    private final StockService stockEntryService;

    @Autowired
    public StockDataFetcher(StockService stockService) {
        this.stockEntryService = stockService;
    }

    @DgsData(parentType= "RECIPE_ITEM_GQL", field="stockEntry")
    public CompletableFuture<StockEntry> stockEntry(DgsDataFetchingEnvironment dfe) {
        StockEntry item = dfe.getSource();
        DataLoader<Long, StockEntry> loader = dfe.getDataLoader(StockEntryDataLoader.class);
        return loader.load(item.getId());
    }

    @DgsQuery
    public List<StockEntry> findAllStockEntries() throws IOException {
        return stockEntryService.findAllStockEntries();
    }
    @DgsQuery
    public List<StockSnapshot> findAllStockSnapshots() throws IOException {
        return stockEntryService.findAllStockSnapshots();
    }

    @DgsQuery
    public StockEntry findStockEntryById(@InputArgument String id) throws IOException {
        return stockEntryService.findById(Long.parseLong(id));
    }
    @DgsQuery
    public int getStockEntryCount() throws IOException {
        return stockEntryService.findAllStockEntries().size();
    }
}