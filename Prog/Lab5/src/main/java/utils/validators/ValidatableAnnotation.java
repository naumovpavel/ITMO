package utils.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that all validator annotations must have
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatableAnnotation {
    public Class<? extends Validatable> validator();
}
