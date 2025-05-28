package diego.soro.model.category.service;

import diego.soro.exception.InvalidArgument;
import diego.soro.model.category.Category;
import diego.soro.model.category.exceptions.CategoryNotFound;
import diego.soro.model.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    @Override
    public List<Category> findProductCategories() {
        return categoryRepository.findProductCategories();
    }

    @Override
    public List<Category> findRawMaterialCategories() {
        return categoryRepository.findRawMaterialCategories();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));
    }
    @Override
    public Category findById(String id) {
        if (!id.isEmpty()){
            return categoryRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new CategoryNotFound(Long.parseLong(id)));
        } else{
            throw new InvalidArgument("Empty id");
        }

    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Category categoryToDelete = findById(id);

        Category parentCategory = categoryToDelete.getParent();
        if (parentCategory == null) {
            throw new InvalidArgument("No se puede eliminar una categoría sin padre (como la raíz)");
        }

        int reassignedProducts = 0;
        int reassignedRawMaterials = 0;
        int reassignedSubcategories = 0;

        // Reasignar productos si hay
        if (!categoryToDelete.getProducts().isEmpty()) {
            categoryToDelete.getProducts().forEach(p -> p.setCategory(parentCategory));
            reassignedProducts = categoryToDelete.getProducts().size();
        }

        // Reasignar materias primas si hay
        if (!categoryToDelete.getRawMaterials().isEmpty()) {
            categoryToDelete.getRawMaterials().forEach(rm -> rm.setCategory(parentCategory));
            reassignedRawMaterials = categoryToDelete.getRawMaterials().size();
        }

        // Reasignar subcategorías si hay
        if (!categoryToDelete.getChildren().isEmpty()) {
            categoryToDelete.getChildren().forEach(child -> child.setParent(parentCategory));
            reassignedSubcategories = categoryToDelete.getChildren().size();
        }

        categoryRepository.delete(categoryToDelete);

        // Podés loguear o devolver este mensaje desde GraphQL
        System.out.printf(
                "Categoría eliminada. Se reasignaron %d producto(s), %d materia(s) prima(s) y %d subcategoría(s) a '%s'%n",
                reassignedProducts, reassignedRawMaterials, reassignedSubcategories, parentCategory.getName()
        );
    }
}
