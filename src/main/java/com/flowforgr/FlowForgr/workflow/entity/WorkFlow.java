package com.flowforgr.FlowForgr.workflow.entity;

import com.flowforgr.FlowForgr.shared.entity.FlowForgrBaseEntity;
import com.flowforgr.FlowForgr.workflow.enums.WorkFlowState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "work_flow")
public class WorkFlow extends FlowForgrBaseEntity {

    @Column(name = "name", columnDefinition = "varchar(100)")
    @ColumnDefault(value = "''")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(150)")
    @ColumnDefault(value = "''")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "worf_flow_step_mapping",
            joinColumns = {@JoinColumn(name = "work_flow_fk")},
            inverseJoinColumns = {@JoinColumn(name = "work_flow_step_fk") })
    private Set<WorkFlowStep> roles = new HashSet<>();

    @Column(name = "work_flow_state")
    @ColumnDefault(value = "Draft")
    @Enumerated(EnumType.STRING)
    private WorkFlowState workFlowState;

    @Column(name = "work_flow_index")
    @ColumnDefault(value = "0")
    private Long workFlowIndex;
}
