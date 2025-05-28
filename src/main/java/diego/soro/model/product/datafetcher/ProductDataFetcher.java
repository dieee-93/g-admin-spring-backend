package diego.soro.model.product.datafetcher;


import com.netflix.graphql.dgs.*;
import diego.soro.graphql.generated.DgsConstants;
import diego.soro.graphql.generated.types.CREATE_PRODUCT_RESPONSE;
import diego.soro.graphql.generated.types.PRODUCT_GQLInput;
import diego.soro.graphql.generated.types.RECIPE_ITEM_GQL;
import diego.soro.model.category.Category;
import diego.soro.model.category.dataloaders.CategoryProductsDataLoader;
import diego.soro.model.product.dataloader.ProductDataLoader;
import diego.soro.model.product.mappers.ProductMapper;
import diego.soro.model.product.Product;
import diego.soro.model.product.service.ProductService;;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class ProductDataFetcher {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductDataFetcher(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @DgsData(parentType = "RECIPE_ITEM_GQL", field = "product")
    public CompletableFuture<Product> product(DgsDataFetchingEnvironment dfe) {
        RECIPE_ITEM_GQL item = dfe.getSource();
        DataLoader<Long, Product> loader = dfe.getDataLoader(ProductDataLoader.class);
        return loader.load(Long.parseLong(item.getId()));
    }
    @DgsData(parentType = "CATEGORY_GQL", field = "products")
    public CompletableFuture<List<Product>> products(DgsDataFetchingEnvironment dfe) {
        Category category = dfe.getSource();
        DataLoader<Long, List<Product>> loader = dfe.getDataLoader(CategoryProductsDataLoader.class);
        return loader.load(category.getId());
    }


    @DgsQuery
    public List<Product> getProducts() throws IOException {
        return productService.getProducts();
    }

    @DgsQuery
    public Product findProductById(@InputArgument String id) throws IOException {
        return productService.findProductById(Long.parseLong(id));
    }
    @DgsQuery
    public int getProductCount() throws IOException {
        return productService.getProducts().size();
    }


    @DgsMutation
    public CREATE_PRODUCT_RESPONSE createProduct(@InputArgument("product") PRODUCT_GQLInput product) {

        try {
            Product toCreate =  productMapper.QQLtoEntity(product);
            Product created = productService.saveProduct(toCreate);
            CREATE_PRODUCT_RESPONSE response = new CREATE_PRODUCT_RESPONSE();
            response.setSuccess(true);
            response.setCode(200);
            response.setMessage("Product created successfully");
            response.setProduct(productMapper.entityToQGL(created));
            return response;

        } catch (Exception e) {
            CREATE_PRODUCT_RESPONSE response = new CREATE_PRODUCT_RESPONSE();
            response.setSuccess(false);
            response.setCode(400);
            response.setMessage("Error creating product");
            return response;
        }
    }

    @DgsMutation
    public Boolean deleteProduct(@InputArgument("id") String id) {
            productService.removeProduct(Long.parseLong(id));
            return true;
    }

}