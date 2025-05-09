package diego.soro.model.category.service;

import diego.soro.model.category.Category;
import diego.soro.model.category.CategoryType;

import java.util.List;

public interface ICategoryService {

    List<Category> getCategoriesByType(CategoryType type);

    Category findById(Long id);

    void save(Category category);

    void remove(Long id);
}
