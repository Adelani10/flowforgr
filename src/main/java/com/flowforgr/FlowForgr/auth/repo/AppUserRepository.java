package com.flowforgr.FlowForgr.auth.repo;

import com.flowforgr.FlowForgr.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    boolean existsByPhoneNumber(String orgEmail);

    boolean existsByEmail(String orgEmail);

    @Modifying
    @Query(value = "insert into app_user_role_mapping (user_fk, role_fk) values (:id, (select id from role where role_name = 'ORGANIZATION_ADMIN' limit 1))", nativeQuery = true)
    void createAppUserRoleMapping(@Param("id") Long id);
}
