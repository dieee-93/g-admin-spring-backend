package diego.soro.model.stock.repository;


import diego.soro.model.stock.StockSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockSnapshotRepository extends JpaRepository<StockSnapshot, Long> {

}
