package diego.soro.validations.raw_material;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinSizeListValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSizeList {
    String message() default "La receta debe contener al menos {value} elementos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value();
}