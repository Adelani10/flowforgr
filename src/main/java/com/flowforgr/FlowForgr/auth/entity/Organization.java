package com.flowforgr.FlowForgr.auth.entity;

import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "organization")
public class Organization extends FlowForgrBaseEntity {

    @Column(name="org_name")
    private String orgName;

    @Column(name="org_short_name", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String orgShortName;

    @Column(name="org_email", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String orgEmail;

    @Column(name="org_phone_number", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String orgPhoneNumber;


    @Column(name="org_address", columnDefinition = "varchar(300)")
    @ColumnDefault(value = "''")
    private String orgAddress;

    @Column(name="org_status")
    @ColumnDefault(value="false")
    private boolean orgStatus;

    @Column(name = "org_contact_person_fk")
    private Long orgContactPersonFk;

//    @Column(name="org_email_verified", columnDefinition = "boolean")
//    @ColumnDefault(value = "false")
//    private boolean orgEmailVerified;
//
//    @Column(name="org_email_verification_date")
//    private LocalDateTime orgEmailVerificationDate;

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = CorporateType.class)
//    @JoinColumn(name="corporate_type_fk")
//    private CorporateType corporateType;
}
