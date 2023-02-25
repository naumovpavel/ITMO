package utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for validate that field less than certain value
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = LessThanValidator.class)
public @interface LessThan {
    public String value() default "0";
}
