package com.flowforgr.FlowForgr.workflow.repo;

import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkFlowStepRepository extends JpaRepository<WorkFlowStep, Long> {

    @Query(value = "select max(w.work_flow_step_index) from work_flow_step_index w", nativeQuery = true)
    Long getLastStepIndex();

    @Query(value = "select * from work_flow_step where id = :id", nativeQuery = true)
    WorkFlowStep findStepById(@Param("id") Long id);
}
