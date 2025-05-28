package diego.soro.model.raw_material.controller;


import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.service.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-material")
public class RawMaterialController {
    @Autowired
    private RawMaterialService service;

    @GetMapping
    public List<RawMaterial> getRawMaterials() {
        return service.findRawMaterials();
    }

    @GetMapping("/{id}")
    public RawMaterial findRawMaterialById(@PathVariable String id) {
        return service.findRawMaterialById(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public void removeRawMaterial(@PathVariable String id) {
        service.removeRawMaterial(Long.parseLong(id));
    }

    @PostMapping
    public void saveRawMaterial(@RequestBody RawMaterial material) {
        service.saveRawMaterial(material);
    }

    @PutMapping("/{id}")
    public void updateRawMaterial(@PathVariable String id, @RequestBody RawMaterial material) {
        material.setId(Long.parseLong(id));
        service.updateRawMaterial(material);
    }


}