package diego.soro.model.category.service;

import diego.soro.model.category.Category;
import diego.soro.model.category.repository.CategoryRepository;
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
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
