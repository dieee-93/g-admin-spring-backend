package diego.soro.model.recipe_item.repository;

import diego.soro.model.raw_material.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeItemRepository extends JpaRepository<RawMaterial, Long> {

}
