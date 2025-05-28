package diego.soro.model.category.service;

import diego.soro.model.category.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAll();

    List<Category> findProductCategories();

    List<Category> findRawMaterialCategories();

    Category findById(Long id);

    Category findById(String id);

    Category save(Category category);

    void remove(Long id);

}
