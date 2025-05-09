package diego.soro.model.category.service;

import diego.soro.model.category.Category;
import diego.soro.model.category.CategoryType;
import diego.soro.model.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoriesByType(CategoryType type) {
        return categoryRepository.findAll()
                .stream()
                .filter(cat -> cat.getType() == type)
                .toList();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
