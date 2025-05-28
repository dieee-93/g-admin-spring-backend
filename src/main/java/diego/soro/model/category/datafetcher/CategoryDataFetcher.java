package diego.soro.model.category.datafetcher;

import com.netflix.graphql.dgs.*;

import diego.soro.graphql.generated.DgsConstants;
import diego.soro.graphql.generated.types.CATEGORY_GQLInput;
import diego.soro.graphql.generated.types.CREATE_CATEGORY_RESPONSE;
import diego.soro.model.category.Category;
import diego.soro.model.category.dataloaders.CategoryDataLoader;
import diego.soro.model.category.dataloaders.CategoryParentDataLoader;
import diego.soro.model.category.mappers.CategoryMapper;
import diego.soro.model.category.service.CategoryService;
import diego.soro.model.raw_material.RawMaterial;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
public class CategoryDataFetcher {
    private final CategoryService categoryService;
    protected final CategoryMapper categoryMapper;

    @Autowired
    public CategoryDataFetcher(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;

    }

    @DgsData(parentType = "RAW_MATERIAL_GQL", field = "category")
    public CompletableFuture<Category> rawMaterialCategory(DgsDataFetchingEnvironment dfe) {
        RawMaterial item = dfe.getSource();
        DataLoader<Long, Category> loader = dfe.getDataLoader(CategoryDataLoader.class);
        return loader.load(item.getCategory().getId());
    }
    @DgsData(parentType = "PRODUCT_GQL", field = "category")
    public CompletableFuture<Category> productCategory(DgsDataFetchingEnvironment dfe) {
        RawMaterial item = dfe.getSource();
        DataLoader<Long, Category> loader = dfe.getDataLoader(CategoryDataLoader.class);
        return loader.load(item.getCategory().getId());
    }
    @DgsData(parentType = "CATEGORY_GQL", field = "parent")
        public CompletableFuture<Category> parentCategory(DgsDataFetchingEnvironment dfe) {
            Category item = dfe.getSource();
            DataLoader<Long, Category> loader = dfe.getDataLoader(CategoryParentDataLoader.class);
            return loader.load(item.getParent().getId());

    }
    @DgsData(parentType = "CATEGORY_GQL", field = "children")
    public CompletableFuture<List<Category>> childrenCategories(DgsDataFetchingEnvironment dfe) {
        Category item = dfe.getSource();
        DataLoader<Long, Category> loader = dfe.getDataLoader(CategoryDataLoader.class);
        return loader.loadMany(item.getChildren().stream().map(Category::getId).collect(Collectors.toList()));
    }

    @DgsQuery
    public List<Category> findAllCategories() throws IOException {
        return categoryService.findAll();
    }

    @DgsQuery
    public List<Category> findAllRawMaterialCategories() throws IOException {
        return categoryService.findRawMaterialCategories();
    }

    @DgsQuery
    public List<Category> findAllProductCategories() throws IOException {
        return categoryService.findProductCategories();
    }

    @DgsQuery
    public Category findCategoryById(@InputArgument String id) throws IOException {

        return categoryService.findById(id);
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
            response.setMessage("Error creating category" + e.getMessage());
            return response;
        }
    }


    @DgsMutation
    public Boolean deleteCategory(@InputArgument("id") String id) {
            categoryService.remove(Long.parseLong(id));
            return true;
    }
}