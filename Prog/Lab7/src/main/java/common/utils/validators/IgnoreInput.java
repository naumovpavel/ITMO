package common.utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for fields which shouldn't be read while building model by client
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatableAnnotation(validator = IgnoreInputValidator.class)
@Documented
public @interface IgnoreInput {

}
