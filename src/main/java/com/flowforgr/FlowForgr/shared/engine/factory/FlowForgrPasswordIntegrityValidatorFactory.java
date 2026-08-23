package com.flowforgr.FlowForgr.shared.engine.factory;

import org.passay.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FlowForgrPasswordIntegrityValidatorFactory {

    private static FlowForgrPasswordIntegrityValidatorFactory _flowForgrPasswordIntegrityValidatorFactory;

    private  PasswordValidator passwordValidator=null;
    private final String SUPPORTED_ALLOWED_SPECIALS = "-!@$%&?><+=*&#|^.";
    private final String CAPITALLETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String SMALLLETTERS = "abcdefghijklmnopqrstuvxyz";
    private final String NUMBERSTRING = "0123456789";

    private FlowForgrPasswordIntegrityValidatorFactory(){
        try{
            passwordValidator = new PasswordValidator(Arrays.asList(
                    new LengthRule(8, 50),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.Digit, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1),
                    //new IllegalSequenceRule(EnglishSequenceData.Numerical, 6, false),
                    //new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 6, false),
                    new CharacterRule(new SpecialCharacterRule(SUPPORTED_ALLOWED_SPECIALS),1),
                    new WhitespaceRule()));
        }catch(Exception e){}
    }

    public static FlowForgrPasswordIntegrityValidatorFactory getInstance(){
        if(_flowForgrPasswordIntegrityValidatorFactory ==null) {
            synchronized (FlowForgrPasswordIntegrityValidatorFactory.class) {
                if (_flowForgrPasswordIntegrityValidatorFactory==null) {
                    _flowForgrPasswordIntegrityValidatorFactory = new FlowForgrPasswordIntegrityValidatorFactory();
                }
            }
        }
        return _flowForgrPasswordIntegrityValidatorFactory;
    }

    public String validateAndCheckPasswordIntegrity(String password){
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
        return (ruleResult.isValid())?"":ruleResult.getDetails().stream()
                .map(RuleResultDetail::getErrorCode).collect(Collectors.joining(",")).toLowerCase();
    }

    public String generatePassword(){
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 10; i++) {
            if (i % 10 == 0) {
                int index = (int) (SUPPORTED_ALLOWED_SPECIALS.length() * Math.random());
                sb.append(SUPPORTED_ALLOWED_SPECIALS.charAt(index));
            } else if (i % 4 == 0) {
                int index = (int) (NUMBERSTRING.length() * Math.random());
                sb.append(NUMBERSTRING.charAt(index));
            } else if (i == 7) {
                int index = (int) (CAPITALLETTERS.length() * Math.random());
                sb.append(CAPITALLETTERS.charAt(index));
            } else {
                int index = (int) (SMALLLETTERS.length() * Math.random());
                sb.append(SMALLLETTERS.charAt(index));
            }
        }

        return sb.toString();
    }

}
