package diego.soro.model.stock.repository;

import diego.soro.model.stock.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

}
