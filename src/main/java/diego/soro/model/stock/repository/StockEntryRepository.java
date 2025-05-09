package diego.soro.model.stock.repository;

import diego.soro.model.stock.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

    @Query("SELECT COALESCE(SUM(e.quantity), 0) FROM StockEntry e WHERE e.rawMaterial.id = :rawMaterialId")
    Double calculateTotalQuantity(@Param("rawMaterialId") Long rawMaterialId);

    @Query("SELECT COALESCE(SUM(e.unitCost * e.quantity) / NULLIF(SUM(e.quantity), 0), 0) FROM StockEntry e WHERE e.rawMaterial.id = :rawMaterialId AND e.unitCost IS NOT NULL")
    Double calculateAverageCost(@Param("rawMaterialId") Long rawMaterialId);
}
