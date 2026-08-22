package com.flowforgr.FlowForgr.auth.entity;


import com.flowforgr.FlowForgr.auth.enums.OtpEvent;
import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "otp")
public class VerificationToken extends FlowForgrBaseEntity {

    @Column(name = "otp")
    @ColumnDefault(value = "''")
    private String otp;

    @Column(name="verification_event", columnDefinition = "varchar(100)")
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'Sign_Up'")
    private OtpEvent verificationEvent;

    @Column(name="request_ip_address", columnDefinition = "varchar(30)")
    @ColumnDefault(value = "''")
    private String requestIpAddress;

    @Column(name="validation_ip_address",columnDefinition = "varchar(30)")
    @ColumnDefault(value = "''")
    private String validationIpAddress;

    @Column(name="request_date")
    private LocalDateTime requestDateTime;

    @Column(name="expired_at")
    private LocalDateTime expiredDateTime;

    @Column(name="validated_date")
    private LocalDateTime validatedDateTime;

    @Column(name="validated_already")
    @ColumnDefault(value = "false")
    private boolean validatedAlready;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_fk")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Organization.class)
    @JoinColumn(name = "organization_fk")
    private Organization organization;
}
