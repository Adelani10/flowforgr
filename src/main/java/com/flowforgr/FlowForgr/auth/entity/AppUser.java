package com.flowforgr.FlowForgr.auth.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.auth.enums.Gender;
import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user", uniqueConstraints = {@UniqueConstraint(name = "app_user_email_unique_info",
        columnNames ={"email","profile_type"}),@UniqueConstraint(name = "app_user_phone_unique_info",
        columnNames ={"phone_number","profile_type"})})
public class AppUser extends FlowForgrBaseEntity {

    @Column(name = "first_name", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String lastName;

    @Column(name = "nickName", columnDefinition = "varchar(150)")
    @ColumnDefault(value = "''")
    private String nickName;

    @Column(name = "email", unique = true)
    @ColumnDefault(value = "''")
    private String email;

    @Column(name = "phone_number", unique = true)
    @ColumnDefault(value = "''")
    private String phoneNumber;

    @Column(name = "password")
    @ColumnDefault(value = "''")
    private String password;

    @Column(name="account_status", columnDefinition = "boolean")
    @ColumnDefault(value = "false")
    private boolean accountStatus;

    @Column(name="is_account_blocked", columnDefinition = "boolean")
    @ColumnDefault(value = "false")
    private boolean isAccountBlocked;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(name = "app_user_role_mapping",
            joinColumns = {@JoinColumn(name = "user_fk")},
            inverseJoinColumns = {@JoinColumn(name = "role_fk") })
    private Set<Role> roles = new HashSet<>();

    @Column(name = "email_verified", columnDefinition = "boolean")
    @ColumnDefault(value = "false")
    private boolean emailVerified;

    @Column(name="gender", columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'Unknown'")
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Organization.class)
    @JoinColumn(name = "organization_fk")
    private Organization organization;

    @JsonProperty("last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "first_login_date")
    private LocalDateTime firstLoginDate;

    @Column(name = "failed_login_attempt_count")
    @ColumnDefault(value = "0")
    private long failedLoginAttemptCount;

    @Column(name="login_attempt_blocked", columnDefinition = "boolean")
    @ColumnDefault(value = "false")
    private boolean loginAttemptBlocked;

    @Column(name = "next_release_date")
    private LocalDateTime nextReleaseDate;

}
