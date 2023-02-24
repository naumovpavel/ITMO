package utils.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatableAnnotation(validator = GraterThanValidator.class)
public @interface GraterThan {
    public String value() default "0";
}
