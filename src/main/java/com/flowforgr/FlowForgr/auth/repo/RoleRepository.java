package com.flowforgr.FlowForgr.auth.repo;

import com.flowforgr.FlowForgr.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String organizationAdmin);

    @Query(value = "select exists (select 1 from role where role_name = :roleName and user_type = :userType " +
            "and organization_fk = :organizationId)", nativeQuery = true)
    boolean existsByRoleNameUserTypeAndOrganization(String roleName, String userType, Long organizationId);
}
