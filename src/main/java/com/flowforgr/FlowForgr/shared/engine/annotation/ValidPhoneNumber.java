package com.flowforgr.FlowForgr.shared.engine.annotation;

import com.flowforgr.FlowForgr.shared.engine.annotation.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface ValidPhoneNumber {


    String message() default "Invalid Phone Number Entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
