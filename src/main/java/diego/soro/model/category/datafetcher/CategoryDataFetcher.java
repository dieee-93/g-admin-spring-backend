package diego.soro.model.category.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import diego.soro.model.category.service.CategoryService;
import diego.soro.model.category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@DgsComponent
public class CategoryDataFetcher {
    private final CategoryService categoryService;

    @Autowired
    public CategoryDataFetcher(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @DgsQuery
    public List<RawMaterialCategory> getRawMaterialCategories() throws IOException {
        return categoryService.getRawMaterialCategories();
    }

    @DgsQuery
    public List<ProductCategory> getProductCategories() throws IOException {
        return categoryService.getProductCategories();
    }

    @DgsQuery
    public RawMaterialCategory findRawMaterialCategoryById(@InputArgument String id) throws IOException {
        return categoryService.findRawMaterialCategoryById(Long.parseLong(id));
    }

    @DgsQuery
    public ProductCategory findProductCategoryById(@InputArgument String id) throws IOException {
        return categoryService.findProductCategoryById(Long.parseLong(id));
    }
    @DgsQuery
    public int getRawMaterialCategoryCount() throws IOException {
        return categoryService.getRawMaterialCategories().size();
    }

    @DgsQuery
    public int getProductCategoryCount() throws IOException {
        return categoryService.getProductCategories().size();
    }
}