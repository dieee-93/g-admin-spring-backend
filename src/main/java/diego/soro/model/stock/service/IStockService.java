package diego.soro.model.stock.service;

import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.StockSnapshot;

import java.util.List;

public interface IStockService {


    StockEntry registerStockMovement(Long rawMaterialId, Double quantity, Double cost, String reason, String notes);
    void updateSnapshot(Long rawMaterialId);
    StockSnapshot getSnapshot(Long rawMaterialId);
    List<StockEntry> getStockEntries();
    StockEntry findById(Long id);

}
