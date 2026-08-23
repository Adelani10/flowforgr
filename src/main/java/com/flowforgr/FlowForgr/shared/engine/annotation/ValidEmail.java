package com.flowforgr.FlowForgr.shared.engine.annotation;


import com.flowforgr.FlowForgr.shared.engine.annotation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "Invalid Email Address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
