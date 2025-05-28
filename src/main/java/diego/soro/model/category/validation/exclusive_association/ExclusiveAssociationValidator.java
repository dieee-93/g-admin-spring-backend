package diego.soro.model.category.validation.exclusive_association;

import diego.soro.model.category.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExclusiveAssociationValidator implements ConstraintValidator<ExclusiveAssociation, Category> {

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context) {

        boolean hasProducts = category.getProducts() != null && !category.getProducts().isEmpty();
        boolean hasRawMaterials = category.getRawMaterials() != null && !category.getRawMaterials().isEmpty();

        return !(hasProducts && hasRawMaterials);
    }
}