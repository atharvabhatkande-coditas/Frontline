package com.coditas.frontline.repository;

import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);

    Page<Users> findByRole(RoleType roleType, Pageable pageable);
}
