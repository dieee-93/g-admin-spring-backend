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

 import java.math.BigDecimal;
 import java.time.LocalDateTime;
 import java.util.List;

@Service
 @RequiredArgsConstructor
 public class StockService implements IStockService {

     @Autowired
     private final StockEntryRepository stockEntryRepository;
    @Autowired
     private final StockSnapshotRepository stockSnapshotRepository;
    @Autowired
    private final RawMaterialRepository rawMaterialRepository;

     public List<StockEntry> findAllStockEntries() {
         return (List<StockEntry>) stockEntryRepository.findAll();
     }
     public StockEntry findById(Long id) {
         return stockEntryRepository.findById(id).orElseThrow(() ->new RuntimeException("Stock entry not found"));
     }
     public List<StockSnapshot> findAllStockSnapshots() {
         return stockSnapshotRepository.findAll();
     }

     @Transactional
     public StockEntry saveStockEntry(Long rawMaterialId, BigDecimal quantity, BigDecimal cost) {
         RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
                 .orElseThrow(() -> new RuntimeException("Raw material not found"));

         StockEntry entry = new StockEntry();
         entry.setRawMaterial(rawMaterial);
         entry.setQuantity(quantity);
         entry.setCost(cost);
         entry.setCreationDate(LocalDateTime.now());
         stockEntryRepository.save(entry);

         updateSnapshot(rawMaterialId);

         return entry;
     }



    public void updateSnapshot(Long rawMaterialId) {

         StockSnapshot snapshot = stockSnapshotRepository.findById(rawMaterialId)
                 .orElse(new StockSnapshot());

         snapshot.setRawMaterialId(rawMaterialId);
         snapshot.setRawMaterial(rawMaterialRepository.findById(rawMaterialId).orElseThrow());

         stockSnapshotRepository.save(snapshot);
     }

     public StockSnapshot getSnapshot(Long rawMaterialId) {
         return stockSnapshotRepository.findById(rawMaterialId)
                 .orElseThrow(() -> new RuntimeException("Stock snapshot not found"));
     }
 }