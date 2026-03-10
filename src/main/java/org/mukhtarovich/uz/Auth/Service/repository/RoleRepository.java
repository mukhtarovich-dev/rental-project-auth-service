package org.mukhtarovich.uz.Auth.Service.repository;

import org.mukhtarovich.uz.Auth.Service.entity.Role;
import org.mukhtarovich.uz.Auth.Service.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}