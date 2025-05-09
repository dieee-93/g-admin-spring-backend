package diego.soro.model.product.service;


import diego.soro.model.product.Product;
import diego.soro.model.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return (List<Product>) repository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Product updateProducts(Long id, Product product) {
        Product productToUpdate = repository.findById(id).get();
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setCategoryId(product.getCategoryId());
        productToUpdate.setProductionCost(product.getProductionCost());
        productToUpdate.setRecipe(product.getRecipe());
        return repository.save(productToUpdate);
    }

    @Override
    public void removeProduct(Long id) {
        repository.deleteById(id);
    }

}
