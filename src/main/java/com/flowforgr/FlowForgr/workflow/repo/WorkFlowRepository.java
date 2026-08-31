package com.flowforgr.FlowForgr.workflow.repo;

import com.flowforgr.FlowForgr.workflow.entity.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkFlowRepository extends JpaRepository<WorkFlow, Long> {
}
