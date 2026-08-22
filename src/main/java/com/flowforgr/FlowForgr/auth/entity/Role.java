package com.flowforgr.FlowForgr.auth.entity;


import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role extends FlowForgrBaseEntity {

    @Column(name="role_name", columnDefinition = "varchar(100)", unique = true)
    private String roleName;

    @Column(name="role_description")
    private String description;

    @Column(name="status", columnDefinition = "boolean")
    @ColumnDefault(value = "true")
    private boolean status;

    @Column(name="is_custom", columnDefinition = "boolean")
    @ColumnDefault(value = "false")
    private boolean isCustom;

//    @Column(name="profile_type", columnDefinition = "varchar(100)")
//    @ColumnDefault(value = "'None'")
//    @Enumerated(EnumType.STRING)
//    private ProfileType profileType;

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Corporate.class)
//    @JoinColumn(name="corporate_fk")
//    private Corporate corporate;
}
