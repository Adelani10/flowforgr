package com.flowforgr.FlowForgr.workflow.repo;

import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkFlowStepRepository extends JpaRepository<WorkFlowStep, Long> {

    @Query(value = "select coalesce(max(w.work_flow_step_index), 0) from work_flow_step w", nativeQuery = true)
    Long getLastStepIndex();

    @Query(value = "select * from work_flow_step where id = :id", nativeQuery = true)
    WorkFlowStep findStepById(@Param("id") Long id);

    @Modifying
    @Query(value = "insert into work_flow_step_mapping (work_flow_step_fk, work_flow_fk) values (:stepId, :workFlowId)", nativeQuery = true)
    void createWorkFlowMapping(@Param("stepId") Long stepId, @Param("workFlowId") Long workFlowId);
}
