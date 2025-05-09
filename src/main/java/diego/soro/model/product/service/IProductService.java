package diego.soro.model.product.service;

import diego.soro.model.product.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);

    List<Product> getProducts();

    Product findProductById(Long id);

    Product updateProducts(Long id, Product product);

    void removeProduct(Long id);


}
