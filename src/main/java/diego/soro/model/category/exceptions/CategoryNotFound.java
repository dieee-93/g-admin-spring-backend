package diego.soro.model.category.exceptions;

import diego.soro.exception.NotFoundException;

public class CategoryNotFound extends NotFoundException {
    public CategoryNotFound(Long id) {
        super("Category not found with id: " + id);
    }

}
