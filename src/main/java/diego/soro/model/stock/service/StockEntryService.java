package diego.soro.model.stock.service;

 import diego.soro.model.raw_material.RawMaterial;
 import diego.soro.model.raw_material.repository.RawMaterialRepository;
 import diego.soro.model.stock.StockEntry;
 import diego.soro.model.stock.StockSnapshot;
 import diego.soro.model.stock.repository.StockEntryRepository;
 import diego.soro.model.stock.repository.StockSnapshotRepository;
 import jakarta.transaction.Transactional;
 import lombok.RequiredArgsConstructor;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.time.LocalDateTime;
 import java.util.List;

@Service
 @RequiredArgsConstructor
 public class StockEntryService implements IStockService {

     @Autowired
     private final StockEntryRepository stockEntryRepository;
    @Autowired
     private final StockSnapshotRepository stockSnapshotRepository;
    @Autowired
    private final RawMaterialRepository rawMaterialRepository;

     public List<StockEntry> getStockEntries() {
         return (List<StockEntry>) stockEntryRepository.findAll();
     }
     public StockEntry findById(Long id) {
         return stockEntryRepository.findById(id).orElseThrow(() ->new RuntimeException("Stock entry not found"));
     }

     @Transactional
     public StockEntry registerStockMovement(Long rawMaterialId, Double quantity, Double cost, String reason, String notes) {
         RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
                 .orElseThrow(() -> new RuntimeException("Raw material not found"));

         StockEntry entry = new StockEntry();
         entry.setRawMaterial(rawMaterial);
         entry.setQuantity(quantity);
         entry.setUnitCost(cost);
         entry.setOperationType(reason);
         entry.setNotes(notes);
         entry.setTimestamp(LocalDateTime.now());
         stockEntryRepository.save(entry);

         updateSnapshot(rawMaterialId);

         return entry;
     }

     public void updateSnapshot(Long rawMaterialId) {
         Double totalQuantity = stockEntryRepository.calculateTotalQuantity(rawMaterialId);
         Double averageCost = stockEntryRepository.calculateAverageCost(rawMaterialId);

         StockSnapshot snapshot = stockSnapshotRepository.findById(rawMaterialId)
                 .orElse(new StockSnapshot());

         snapshot.setRawMaterialId(rawMaterialId);
         snapshot.setRawMaterial(rawMaterialRepository.findById(rawMaterialId).orElseThrow());
         snapshot.setTotalQuantity(totalQuantity);
         snapshot.setAverageCost(averageCost);
         snapshot.setLastUpdated(LocalDateTime.now());

         stockSnapshotRepository.save(snapshot);
     }

     public StockSnapshot getSnapshot(Long rawMaterialId) {
         return stockSnapshotRepository.findById(rawMaterialId)
                 .orElseThrow(() -> new RuntimeException("Stock snapshot not found"));
     }
 }