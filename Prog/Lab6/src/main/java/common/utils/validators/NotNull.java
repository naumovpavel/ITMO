package common.utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for validate that fields value isn't null
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = NotNullValidator.class)
public @interface NotNull {
}