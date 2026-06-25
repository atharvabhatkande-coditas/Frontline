package com.coditas.frontline.repository;

import com.coditas.frontline.entity.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment,Long> {
    Optional<TicketAssignment> findByTicket_TicketNoAndIsCurrentAgent(String ticketNo, boolean b);
}
