package com.coditas.frontline.repository;

import com.coditas.frontline.entity.Ticket;

import com.coditas.frontline.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findBySubjectAndCustomer_Id(String subject, Long customerId);

    Optional<Ticket> findByTicketNo(String ticketNo);

    Page<Ticket> findByCustomer_IdAndTicketStatus(Long id, TicketStatus ticketStatus, Pageable pageable);

    Page<Ticket> findByCustomer_Id(Long id, Pageable pageable);
}
