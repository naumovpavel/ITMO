package utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for validate that field grater or equal than certain value
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = GraterThanValidator.class)
public @interface GraterThan {
    public String value() default "0";
}
