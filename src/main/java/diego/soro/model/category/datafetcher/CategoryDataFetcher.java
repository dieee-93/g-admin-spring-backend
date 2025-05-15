package diego.soro.model.category.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import diego.soro.graphql.generated.types.CATEGORY_GQLInput;
import diego.soro.graphql.generated.types.CREATE_CATEGORY_RESPONSE;
import diego.soro.model.category.Category;
import diego.soro.model.category.mappers.CategoryMapper;
import diego.soro.model.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@DgsComponent
public class CategoryDataFetcher {
    private final CategoryService categoryService;
    protected final CategoryMapper categoryMapper;

    @Autowired
    public CategoryDataFetcher(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;

    }

    @DgsQuery
    public List<Category> findAll() throws IOException {
        return categoryService.findAll();
    }

    @DgsQuery
    public List<Category> findAllRawMaterialCategory() throws IOException {
        return categoryService.findRawMaterialCategories();
    }

    @DgsQuery
    public List<Category> findAllProductCategory() throws IOException {
        return categoryService.findProductCategories();
    }

    @DgsQuery
    public Category findCategoryById(@InputArgument String id) throws IOException {
        return categoryService.findById(Long.parseLong(id));
    }

    @DgsQuery
    public int getRawMaterialCategoryCount() throws IOException {
        return categoryService.findRawMaterialCategories().size();
    }

    @DgsQuery
    public int getProductCategoryCount() throws IOException {
        return categoryService.findProductCategories().size();
    }
    @DgsQuery
    public int getCategoryCount() throws IOException {
        return categoryService.findAll().size();
    }
   @DgsMutation
    public CREATE_CATEGORY_RESPONSE createCategory(@InputArgument CATEGORY_GQLInput category) {
        try {
            Category toCreate = categoryMapper.GQLToEntity(category);
            Category created = categoryService.save(toCreate);
            CREATE_CATEGORY_RESPONSE response = new CREATE_CATEGORY_RESPONSE();
            response.setSuccess(true);
            response.setCode(200);
            response.setMessage("Category created successfully");
            response.setCategory(categoryMapper.entityToGQL(created));
            return response;

        } catch (Exception e) {
            CREATE_CATEGORY_RESPONSE response = new CREATE_CATEGORY_RESPONSE();
            response.setSuccess(false);
            response.setCode(400);
            response.setMessage("Error creating category");
            return response;
        }
    }

    @DgsMutation
    public Category updateCategory(@InputArgument("id") String id,
                                   @InputArgument("category") CategoryInput input) {
        return categoryService.updateCategory(Long.parseLong(id), input);
    }

    @DgsMutation
    public Boolean deleteCategory(@InputArgument("id") String id) {
            categoryService.remove(Long.parseLong(id));
            return true;
    }
}