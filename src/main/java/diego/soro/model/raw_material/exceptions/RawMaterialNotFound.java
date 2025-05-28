package diego.soro.model.raw_material.exceptions;

import diego.soro.exception.NotFoundException;

public class RawMaterialNotFound extends NotFoundException {
    public RawMaterialNotFound(Long id) {
        super("Raw material not found with id: " + id);
    }
}
