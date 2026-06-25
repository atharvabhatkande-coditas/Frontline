package com.coditas.frontline.repository;

import com.coditas.frontline.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    Optional<Chat> findByTicket_TicketNo(String ticketId);
}
