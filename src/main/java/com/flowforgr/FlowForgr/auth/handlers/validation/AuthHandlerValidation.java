package com.flowforgr.FlowForgr.auth.handlers.validation;


import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.records.AuthorityInfo;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.auth.repo.AppUserRepository;
import com.flowforgr.FlowForgr.auth.repo.OrganizationRepository;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrGeneratedAuthToken;
import com.flowforgr.FlowForgr.shared.security.config.AppJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AuthHandlerValidation {

    private final AppUserRepository appUserRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppJwtService appJwtService;


    public ValidationResult validateRegisterAppUserOrganization(FlowForgrRegisterOrganizationRequest flowForgrRegisterOrganizationRequest) {

        ValidationResult result = new ValidationResult(true, "");

        if(isOrgExistByEmail(flowForgrRegisterOrganizationRequest.getEmail())){
            return new ValidationResult(false, "Organization with email already exists");
        }

        if(isOrgExistByPhone(flowForgrRegisterOrganizationRequest.getOrgPhoneNumber())) {
            return new ValidationResult(false, "Organization with phone number already exists");
        }

        if(isAppUserExistByEmail(flowForgrRegisterOrganizationRequest.getEmail())){
            return new ValidationResult(false, "App user with email already exists");
        }

        if(isAppUserExistByPhone(flowForgrRegisterOrganizationRequest.getOrgPhoneNumber())) {
            return new ValidationResult(false, "App user with phone number already exists");
        }

        if(!isPasswordNotSameAsUsername(flowForgrRegisterOrganizationRequest.getPassword(), flowForgrRegisterOrganizationRequest.getEmail())) {
            return new ValidationResult(false, "Password cannot be same as email");
        }

        if(!isNewAndConfirmPasswordMatches(flowForgrRegisterOrganizationRequest.getPassword(), flowForgrRegisterOrganizationRequest.getConfirmPassword())) {
            return new ValidationResult(false, "Passwords don't match");
        }

        return result;
    }


    public boolean isOrgExistByEmail(String orgEmail) {
        return organizationRepository.existsByOrgEmail(orgEmail);
    }

    public boolean isOrgExistByPhone(String orgPhone) {
        return organizationRepository.existsByOrgPhoneNumber(orgPhone);

    }

    public boolean isAppUserExistByEmail(String orgEmail) {
        return appUserRepository.existsByEmail(orgEmail);
    }

    public boolean isAppUserExistByPhone(String orgEmail) {
        return appUserRepository.existsByPhoneNumber(orgEmail);
    }


    public boolean isLoginPasswordMatches(String rawPassword, AppUser appUsersRecord) {
        return passwordEncoder.matches(rawPassword,appUsersRecord.getPassword());
    }

    public boolean isCurrentPasswordMatches(String rawPassword, String hashedPassword) {
        return  passwordEncoder.matches(rawPassword,hashedPassword);
    }

    public boolean isNewPasswordDifferentFromPreviousMatches(String newPassword, String hashedPassword, Locale requestLanguage) {
        return passwordEncoder.matches(newPassword,hashedPassword);

    }

    public boolean isNewAndConfirmPasswordMatches(String newPassword, String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }

    public boolean isPasswordNotSameAsUsername(String newPassword, String email) {
        return !newPassword.equals(email);
    }

    public boolean isConfirmPasswordNotSameAsUsername(String confirmPassword, String username, Locale requestLanguage) {
       return confirmPassword.equals(username);
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


    public FlowForgrGeneratedAuthToken generateAuthToken(AppUser appUser) {
        String token = appJwtService.generateToken(appUser);
        List<AuthorityInfo> authorityInfoList = appUser.getRoles().stream()
                .map(r -> new AuthorityInfo(r.getRoleName(), r.getId())).toList();

        return FlowForgrGeneratedAuthToken.builder().token(token)
                .expiredTime(LocalDateTime.now().plusHours(2))
                .expirationMinutes(120).authorityInfos(authorityInfoList)
                .creationAt(LocalDateTime.now()).expirationDate(LocalDateTime.now().plusHours(2))
                .build();
    }
}
