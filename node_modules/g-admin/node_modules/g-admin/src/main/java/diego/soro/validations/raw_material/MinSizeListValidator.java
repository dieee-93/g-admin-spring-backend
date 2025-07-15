package diego.soro.validations.raw_material;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MinSizeListValidator implements ConstraintValidator<MinSizeList, List<?>> {
    private int minSize;

    @Override
    public void initialize(MinSizeList constraintAnnotation) {
        this.minSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return value != null && value.size() >= minSize;
    }
}
