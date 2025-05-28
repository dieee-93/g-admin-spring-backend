package diego.soro.model.product.repository;

import diego.soro.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory_IdIn(Collection<Long> categoryIds);

}
