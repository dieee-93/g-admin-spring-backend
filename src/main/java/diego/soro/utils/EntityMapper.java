package diego.soro.utils;



import diego.soro.graphql.generated.types.RawMaterialInput;
import diego.soro.model.raw_material.RawMaterial;

import java.time.LocalDate;

public class EntityMapper {

    public static RawMaterial rawMaterialInputToEntity(RawMaterialInput rawMaterialInput, RawMaterial rawMaterial){
        if (rawMaterialInput.getName() != null)
            rawMaterial.setName(rawMaterialInput.getName());

        if (rawMaterialInput.getType() != null)
            rawMaterial.setType(rawMaterialInput.getType());

        if (rawMaterialInput.getLatitude() != null)
            rawMaterial.setLatitude(rawMaterialInput.getLatitude());

        if (rawMaterialInput.getLongitude() != null)
            rawMaterial.setLongitude(rawMaterialInput.getLongitude());

        if (rawMaterialInput.getPurchaseDate() != null)
            rawMaterial.setPurchaseDate(LocalDate.parse(rawMaterialInput.getPurchaseDate()));

        if (rawMaterialInput.getAgencyId() != null)
            rawMaterial.setAgency(agency);

        return rawMaterial;
    }
}