package diego.soro.model.raw_material.repository;

import diego.soro.model.product.Product;
import diego.soro.model.raw_material.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    public List<RawMaterial> findByCategory_IdIn(Collection<Long> categoryIds);
}
