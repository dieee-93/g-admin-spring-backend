package diego.soro.model.raw_material.service;


import diego.soro.model.raw_material.RawMaterial;

import java.util.List;

public interface IRawMaterialService {

    List<RawMaterial> getRawMaterials();

    RawMaterial findRawMaterialById(Long id);

    void removeRawMaterial(Long id);

    void saveRawMaterial(RawMaterial rawMaterial);

    void updateRawMaterial(RawMaterial rawMaterial);
}
