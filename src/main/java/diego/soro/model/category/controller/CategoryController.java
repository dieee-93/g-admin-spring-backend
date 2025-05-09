package diego.soro.model.category.controller;


import diego.soro.model.category.service.CategoryService;
import diego.soro.model.category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/api/raw-material-cat")
    public List<RawMaterialCategory> getRawMaterialCategories() {
        return service.getRawMaterialCategories();
    }

    @GetMapping("/api/raw-material-cat/{id}")
    public RawMaterialCategory findRawMaterialCategoryById(@PathVariable String id) {
        return service.findRawMaterialCategoryById(Long.parseLong(id));
    }

    @PostMapping("/api/raw-material-cat")
    public void saveRawMaterialCategory(@RequestBody RawMaterialCategory category) {
        service.saveRawMaterialCategory(category);
    }


    @DeleteMapping("/api/raw-material-cat/{id}")
    public void removeRawMaterialCategory(@PathVariable String id) {
        service.removeRawMaterialCategory(Long.parseLong(id));
    }

    @GetMapping("/api/product-cat")
    public List<ProductCategory> getProductCategories() {
        return service.getProductCategories();
    }

    @GetMapping("/api/product-cat/{id}")
    public ProductCategory findProductCategoryById(@PathVariable String id) {
        return service.findProductCategoryById(Long.parseLong(id));
    }

    @PostMapping("/api/product-cat")
    public void saveProductCategory(@RequestBody ProductCategory categoria) {
        service.saveProductCategory(categoria);
    }

    @DeleteMapping("/api/product-cat/{id}")
    public void removeProductCategory(@PathVariable String id) {
        service.removeProductCategory(Long.parseLong(id));
    }


}
