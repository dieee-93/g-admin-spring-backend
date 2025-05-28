package diego.soro.model.raw_material.service;


import diego.soro.model.raw_material.RawMaterial;

import java.util.List;

public interface IRawMaterialService {

    List<RawMaterial> findRawMaterials();

    RawMaterial findRawMaterialById(Long id);

    RawMaterial findRawMaterialById(String id);

    void removeRawMaterial(Long id);

    RawMaterial saveRawMaterial(RawMaterial rawMaterial);

    void updateRawMaterial(RawMaterial rawMaterial);
}
