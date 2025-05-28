package diego.soro.model.category.validation.exclusive_association;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExclusiveAssociationValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExclusiveAssociation {
    String message() default "Una categoría no puede tener simultáneamente productos y materias primas.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}