package common.utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for auto generated fields
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatableAnnotation(validator = AutoGeneratedValidator.class)
@Documented
public @interface AutoGenerated {
}
