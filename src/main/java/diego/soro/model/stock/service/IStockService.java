package diego.soro.model.stock.service;

import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.StockSnapshot;

import java.math.BigDecimal;
import java.util.List;

public interface IStockService {


    StockEntry saveStockEntry(Long rawMaterialId, BigDecimal quantity, BigDecimal cost);
    void updateSnapshot(Long rawMaterialId);
    StockSnapshot getSnapshot(Long rawMaterialId);
    List<StockEntry> findAllStockEntries();
    List<StockSnapshot> findAllStockSnapshots();
    StockEntry findById(Long id);

}
