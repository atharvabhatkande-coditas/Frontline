package com.coditas.frontline.repository;

import com.coditas.frontline.entity.Invitation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    Optional<Invitation> findByUsernameAndCode(String username,String code);
}
