package diego.soro.model.product.datafetcher;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import diego.soro.graphql.generated.types.CreateProductResponse;
import diego.soro.graphql.generated.types.ProductInput;
import diego.soro.model.product.mappers.ProductMapper;
import graphql
import diego.soro.model.product.Product;
import diego.soro.model.product.service.ProductService;;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@DgsComponent
public class ProductDataFetcher {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductDataFetcher(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
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
    public CreateProductResponse createProduct(@InputArgument("product") ProductInput input) {
        try {
            Product toCreate =  productMapper.toEntity(input);
            Product created = productService.saveProduct(toCreate);
            CreateProductResponse response = new CreateProductResponse();
            response.setSuccess(true);
            response.setCode(200);
            response.setMessage("Product created successfully");
            response.setProduct(created);
            return response;

        } catch (Exception e) {
            CreateProductResponse response = new CreateProductResponse();
            response.setSuccess(false);
            response.setCode(400);
            response.setMessage("Error creating product");
            return response;
        }
    }

    @DgsMutation
    public Product updateProduct(@InputArgument("id") String id,
                                 @InputArgument("product") ProductInput input) {
        return productService.updateProducts(Long.parseLong(id), input);
    }

    @DgsMutation
    public Boolean deleteProduct(@InputArgument("id") String id) {
            productService.removeProduct(Long.parseLong(id));
            return true;
    }

}