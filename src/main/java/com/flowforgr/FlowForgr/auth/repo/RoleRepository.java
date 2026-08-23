package com.flowforgr.FlowForgr.auth.repo;

import com.flowforgr.FlowForgr.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String organizationAdmin);
}
