package com.flowforgr.FlowForgr.auth.repo;

import com.flowforgr.FlowForgr.auth.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByOrgEmail(String orgEmail);

    boolean existsByOrgPhoneNumber(String orgPhone);
}
