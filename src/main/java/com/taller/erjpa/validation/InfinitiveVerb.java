package com.taller.erjpa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = InfinitiveVerbValidator.class)
@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface InfinitiveVerb {
    String message() default "{formato.objetivos.infinitive}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
