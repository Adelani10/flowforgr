package com.flowforgr.FlowForgr.auth.handlers.impl;

import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.entity.Organization;
import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.handlers.AuthHandler;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrLoginRequest;
import com.flowforgr.FlowForgr.auth.payload.response.FlowForgrLoginResponse;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.handlers.validation.AuthHandlerValidation;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.auth.repo.AppUserRepository;
import com.flowforgr.FlowForgr.auth.repo.OrganizationRepository;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrGeneratedAuthToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import static com.flowforgr.FlowForgr.auth.builders.record.FlowForgrAppUserRecordBuilder.buildOrganizationAppUserRecordOperation;
import static com.flowforgr.FlowForgr.auth.builders.record.FlowForgrAppUserRecordBuilder.buildOrganizationRecordOperation;
import static com.flowforgr.FlowForgr.auth.builders.response.FlowForgrAuthPayloadBuilder.buildFlowForgrLoginResponse;
import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createFailureResponse;
import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createSuccessResponse;


@Service
@RequiredArgsConstructor
public class AuthHandlerImpl implements AuthHandler {

    private final AuthHandlerValidation authHandlerValidation;
    private final AppUserRepository appUserRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${ALLOWED_FAILED_LOGIN_ATTEMPTS}")
    private String allowedFailedLoginAttempts;

    /**
     * @param flowForgrRegisterOrganizationRequest
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<FlowForgrApiResponse<?>> handleRegisterAppUserOrganization(FlowForgrRegisterOrganizationRequest flowForgrRegisterOrganizationRequest) {

        ValidationResult response = authHandlerValidation.validateRegisterAppUserOrganization(flowForgrRegisterOrganizationRequest);

        if(!response.isValid()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createFailureResponse("Error", response.errorMessage()));
        }

        String hashedPassword = authHandlerValidation.hashPassword(flowForgrRegisterOrganizationRequest.getPassword());
        AppUser appUser = buildOrganizationAppUserRecordOperation(flowForgrRegisterOrganizationRequest, hashedPassword);

        Role role = roleRepository.findByRoleName("ORGANIZATION_ADMIN");
        if(!ObjectUtils.isEmpty(role)) {
            appUser.getRoles().add(role);
        }
//        appUserRepository.createAppUserRoleMapping(savedUser.getId());

        Organization org = buildOrganizationRecordOperation(flowForgrRegisterOrganizationRequest);
        appUser.setOrganization(org);
        AppUser savedUser = appUserRepository.save(appUser);
        Organization savedOrg = organizationRepository.save(org);

        //Todo Send email otp

        FlowForgrGeneratedAuthToken flowForgrGeneratedAuthToken = authHandlerValidation.generateAuthToken(savedUser);
        FlowForgrLoginResponse res = buildFlowForgrLoginResponse(appUser, savedOrg, flowForgrGeneratedAuthToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse(res, "Organization created successfully"));
    }

    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleTest(AuthIdentity authIdentity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse("Success", "Test successful for " + authIdentity.getEmail()));
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleFLowForgrLoginAppUser(FlowForgrLoginRequest request) {

        AppUser appUser = appUserRepository.findByEmail(request.getUsername());
        if(ObjectUtils.isEmpty(appUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Error", "Invalid credentials"));
        }

        ValidationResult validationResult = authHandlerValidation.validateLoginAppUserOrganization(request, appUser);
        AppUser savedUser;
        if(!validationResult.isValid()) {
            if(validationResult.errorMessage().equalsIgnoreCase("Invalid credentials")) {
                appUser.setFailedLoginAttemptCount(appUser.getFailedLoginAttemptCount() + 1);
                if(appUser.getFailedLoginAttemptCount() >= Long.parseLong(allowedFailedLoginAttempts) && ObjectUtils.isEmpty(appUser.getNextReleaseDate())) {
                    appUser.setLoginAttemptBlocked(true);
                    appUser.setNextReleaseDate(LocalDateTime.now().plusMinutes(30));
                }
                appUserRepository.save(appUser);
            }
            System.out.println(validationResult.errorMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Error", validationResult.errorMessage()));
        }

        appUser.setFirstLoginDate(!ObjectUtils.isEmpty(appUser.getFirstLoginDate()) ? appUser.getFirstLoginDate() : LocalDateTime.now());
        appUser.setLastLoginDate(LocalDateTime.now());
        appUser.setFailedLoginAttemptCount(0L);
        appUser.setLoginAttemptBlocked(false);
        appUser.setNextReleaseDate(null);
        savedUser = appUserRepository.save(appUser);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(savedUser.getEmail(), request.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Error", e.getMessage()));
        }

        FlowForgrGeneratedAuthToken flowForgrGeneratedAuthToken = authHandlerValidation.generateAuthToken(savedUser);
        FlowForgrLoginResponse res = buildFlowForgrLoginResponse(appUser, savedUser.getOrganization(), flowForgrGeneratedAuthToken);

        //Todo Handle Audit Trail
        return ResponseEntity.status(HttpStatus.OK).body(createSuccessResponse(res, "Login successful"));
    }
}
