package diego.soro.model.product.controller;


import diego.soro.model.product.Product;
import diego.soro.model.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/api/product")
    public List<Product> getAll() {
        return service.getProducts();
    }

    @GetMapping("/api/product/{id}")
    public Product get(@PathVariable String id) {
        return service.findProductById(Long.parseLong(id));
    }

    @DeleteMapping("/api/product/{id}")
    public void remove(@PathVariable String id) {
        service.removeProduct(Long.parseLong(id));
    }

    @PostMapping("/api/product")
    public void save(@RequestBody Product product) {
        service.saveProduct(product);
    }


}