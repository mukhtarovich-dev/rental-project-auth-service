package org.mukhtarovich.uz.Auth.Service.repository;

import org.mukhtarovich.uz.Auth.Service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByPhoneNumberAndIsActive(String username, boolean isActive);
}
