package com.eurostat.eurostattest.service.annotations;

import com.eurostat.eurostattest.service.validators.FilterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FilterValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterConstraint {
    String message() default "Invalid filter field";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
