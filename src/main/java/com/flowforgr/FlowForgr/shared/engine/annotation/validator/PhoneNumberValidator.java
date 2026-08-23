package com.flowforgr.FlowForgr.shared.engine.annotation.validator;

import com.flowforgr.FlowForgr.shared.engine.annotation.ValidPhoneNumber;
import com.flowforgr.FlowForgr.shared.util.FlowForgrStringUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public void initialize(final ValidPhoneNumber constraintAnnotation) {}

    @Override
    public boolean isValid(final String phoneNumber, final ConstraintValidatorContext context) {
        if(FlowForgrStringUtil.isNotBlank(phoneNumber)) {
            return validatePhoneFormat(phoneNumber);
        }
        return true;
    }

    private boolean validatePhoneFormat(final String phoneNumber) {
        return ((phoneNumber.length() >=9 && phoneNumber.length()  <=11)  || (phoneNumber.length() >=9 && phoneNumber.length()  <=14 && phoneNumber.startsWith("+"))
                || ((phoneNumber.length()==13 && phoneNumber.startsWith("234")) || ((phoneNumber.length()==14 && phoneNumber.startsWith("+234")))));
    }
}
