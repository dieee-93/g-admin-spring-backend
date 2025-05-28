package diego.soro.model.recipe_item.repository;

import diego.soro.model.recipe_item.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
    List<RecipeItem> findByStockEntry_IdIn(Collection<Long> stockEntryIds);
    List<RecipeItem> findByProduct_IdIn(Collection<Long> productIds);
}
