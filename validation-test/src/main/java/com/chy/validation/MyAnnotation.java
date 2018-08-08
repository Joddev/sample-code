package com.chy.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = MyValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String message() default "Invalid!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
    boolean upperCase() default false;
    int min() default 8;
    int max() default 20;
}
