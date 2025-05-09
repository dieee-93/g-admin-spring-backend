package diego.soro.model.stock.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.service.StockEntryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@DgsComponent
public class StockDataFetcher {
    private final StockEntryService stockEntryService;

    @Autowired
    public StockDataFetcher(StockEntryService stockService) {
        this.stockEntryService = stockService;
    }

    @DgsQuery
    public List<StockEntry> getStockEntries() throws IOException {
        return stockEntryService.getStockEntries();
    }

    @DgsQuery
    public StockEntry findStockEntryById(@InputArgument String id) throws IOException {
        return stockEntryService.findById(Long.parseLong(id));
    }
    @DgsQuery
    public int getStockEntryCount() throws IOException {
        return stockEntryService.getStockEntries().size();
    }
}