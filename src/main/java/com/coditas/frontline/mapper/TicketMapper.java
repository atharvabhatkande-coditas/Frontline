package com.coditas.frontline.mapper;

import com.coditas.frontline.dto.response.AllTicketResponse;
import com.coditas.frontline.dto.response.TicketResponse;
import com.coditas.frontline.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public TicketResponse ticketResponse(Ticket ticket, String ticketAssignment){
        return TicketResponse.builder()
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .ticketStatus(ticket.getTicketStatus())
                .assignedAgentEmail(ticketAssignment)
                .build();
    }

    public AllTicketResponse allTicketResponses(Ticket ticket){
        return AllTicketResponse.builder()
                .ticketNumber(ticket.getTicketNo())
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .ticketStatus(ticket.getTicketStatus())
                .build();
    }
}
