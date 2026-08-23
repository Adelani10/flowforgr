package com.flowforgr.FlowForgr.shared.engine.annotation.validator;


import com.flowforgr.FlowForgr.shared.engine.annotation.ValidPassword;
import com.flowforgr.FlowForgr.shared.engine.factory.FlowForgrPasswordIntegrityValidatorFactory;
import com.flowforgr.FlowForgr.shared.util.FlowForgrStringUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {}

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        if(FlowForgrStringUtil.isNotBlank(password)){
            String isValidEmptyResult = FlowForgrPasswordIntegrityValidatorFactory
                    .getInstance().validateAndCheckPasswordIntegrity(password);
            if(FlowForgrStringUtil.isBlank(isValidEmptyResult)){
                return true;
            }else{
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(isValidEmptyResult).addConstraintViolation();
            }
            return false;
        }else {
            return true;
        }
    }

}