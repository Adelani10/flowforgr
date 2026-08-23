package com.flowforgr.FlowForgr.auth.handlers.impl;

import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.entity.Organization;
import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.handlers.AuthHandler;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        AppUser savedUser = appUserRepository.save(appUser);
//        appUserRepository.createAppUserRoleMapping(savedUser.getId());


        Organization org = buildOrganizationRecordOperation(flowForgrRegisterOrganizationRequest);
        Organization savedOrg = organizationRepository.save(org);

        //Todo Send email otp

        FlowForgrGeneratedAuthToken flowForgrGeneratedAuthToken = authHandlerValidation.generateAuthToken(savedUser);
        FlowForgrLoginResponse res = buildFlowForgrLoginResponse(appUser, savedOrg, flowForgrGeneratedAuthToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse(res, "Organization created successfully"));
    }
}
