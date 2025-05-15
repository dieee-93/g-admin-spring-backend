package diego.soro.model.recipe_item.service;


import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.recipe_item.repository.RecipeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService implements IRawMaterialService {
    @Autowired
    private RecipeItemRepository repository;

    @Override
    public List<RawMaterial> getRawMaterials() {
        return (List<RawMaterial>) repository.findAll();
    }

    @Override
    public RawMaterial findRawMaterialById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void removeRawMaterial(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void saveRawMaterial(RawMaterial rawMaterial) {
        repository.save(rawMaterial);
    }

    @Override
    public void updateRawMaterial(RawMaterial rawMaterial) {
        repository.save(rawMaterial);
    }
}
