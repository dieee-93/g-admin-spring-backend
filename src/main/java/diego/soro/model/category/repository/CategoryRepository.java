package diego.soro.model.category.repository;

import diego.soro.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Categorías donde la lista de productos está vacía (categorías de materias primas)
    @Query("SELECT DISTINCT c FROM Category c JOIN c.rawMaterials rm")
    List<Category> findProductCategories();

    // Categorías donde la lista de materias primas está vacía (categorías de productos)
    @Query("SELECT DISTINCT c FROM Category c JOIN c.rawMaterials rm")
    List<Category> findRawMaterialCategories();

}
