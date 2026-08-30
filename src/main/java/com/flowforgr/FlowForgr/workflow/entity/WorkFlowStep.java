package com.flowforgr.FlowForgr.workflow.entity;


import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "work_flow_step")
public class WorkFlowStep extends FlowForgrBaseEntity {

    @Column(name = "name", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(150)")
    @ColumnDefault(value = "''")
    private String description;

    @JoinColumn(name = "assigned_role", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Role assignedRole;
}
