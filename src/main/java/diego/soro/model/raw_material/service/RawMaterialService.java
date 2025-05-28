package diego.soro.model.raw_material.service;


import diego.soro.exception.InvalidArgument;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.exceptions.RawMaterialNotFound;
import diego.soro.model.raw_material.repository.RawMaterialRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService implements IRawMaterialService {
    @Autowired
    private RawMaterialRepository repository;

    @Override
    public List<RawMaterial> findRawMaterials() {
        return (List<RawMaterial>) repository.findAll();
    }

    @Override
    public RawMaterial findRawMaterialById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RawMaterialNotFound(id));
    }
    @Override
    public RawMaterial findRawMaterialById(String id) {
        if (!id.isEmpty()){
            return repository.findById(Long.parseLong(id)).orElseThrow(() -> new RawMaterialNotFound(Long.parseLong(id)));
        } else {
            throw  new InvalidArgument("Empty id");
        }
    }

    @Override
    public void removeRawMaterial(Long id) {
        repository.deleteById(id);
    }

    @Override
    public RawMaterial saveRawMaterial(@Valid RawMaterial rawMaterial) {
       return repository.save(rawMaterial);
    }

    @Override
    public void updateRawMaterial(RawMaterial rawMaterial) {
        repository.save(rawMaterial);
    }
}
