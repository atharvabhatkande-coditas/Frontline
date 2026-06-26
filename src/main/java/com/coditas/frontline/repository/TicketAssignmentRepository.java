package com.coditas.frontline.repository;

import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment,Long> {
    Optional<TicketAssignment> findByTicket_TicketNoAndIsCurrentAgent(String ticketNo, boolean b);


    Optional<TicketAssignment> findByTicket_TicketNoAndAgent_Id(String ticketNo, Long agentId);


    Page<TicketAssignment> findByAgent_Id(Long agentId, Pageable pageable);

    Optional<TicketAssignment> findByTicket_TicketNoAndAgent_Username(String ticketNo, String username);
}
