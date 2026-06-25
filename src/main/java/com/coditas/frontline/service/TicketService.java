package com.coditas.frontline.service;

import com.coditas.frontline.constants.AuthConstants;
import com.coditas.frontline.dto.request.OpenTicketRequest;
import com.coditas.frontline.dto.response.*;
import com.coditas.frontline.entity.Customer;
import com.coditas.frontline.entity.Ticket;
import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.enums.TicketStatus;
import com.coditas.frontline.exception.AlreadyExistException;
import com.coditas.frontline.exception.AuthorizationException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.mapper.TicketMapper;
import com.coditas.frontline.repository.TicketAssignmentRepository;
import com.coditas.frontline.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.coditas.frontline.constants.ExceptionConstants.EXIST;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.*;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final TicketMapper ticketMapper;

    @Transactional
    public SingleResponse openTicket( OpenTicketRequest openTicketRequest, Customer customer) {
        Ticket ticket=ticketRepository.findBySubjectAndCustomer_Id(openTicketRequest.getSubject(),customer.getId())
                .orElse(null);

        if(!Objects.isNull(ticket)){
            throw new AlreadyExistException(TICKET+EXIST);
        }
        String ticketNo=UUID.randomUUID().toString().toUpperCase().replace("-","").substring(0,10);
        Ticket newTicket=Ticket.builder()
                .subject(openTicketRequest.getSubject())
                .description(openTicketRequest.getDescription())
                .ticketStatus(TicketStatus.OPEN)
                .customer(customer)
                .priority(openTicketRequest.getPriority())
                .ticketNo(ticketNo)
                .build();

        ticketRepository.save(newTicket);

        return SingleResponse.builder()
                .message(TICKET_OPENED)
                .build();
    }

    public TicketResponse getTicketDetails(Customer customer, String ticketNo) {

        Ticket ticket=ticketRepository.findByTicketNo(ticketNo)
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));

        if(!Objects.equals(customer.getId(),ticket.getCustomer().getId())){
            throw new AuthorizationException(AuthConstants.UNAUTHORIZED);
        }

        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(ticket.getTicketNo(),true)
                .orElse(null);
        String assignedAgent=null;
        if(Objects.isNull(ticketAssignment)){
            assignedAgent=TICKET_NOT_ASSIGNED;
        }

        return ticketMapper.ticketResponse(ticket,assignedAgent);
    }

    public PageResponse<AllTicketResponse> getAllTicket(Customer customer, String status,int page,int size,String name,String sortDirection) {
        Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ?Sort.by(name).ascending()
                : Sort.by(name).descending();
        Pageable pageable= PageRequest.of(page,size,sort);

        if(status==null){
            Page<Ticket>tickets=ticketRepository.findByCustomer_Id(customer.getId(),pageable);
            List<AllTicketResponse>allTicketResponses= tickets
                    .stream()
                    .map(ticketMapper::allTicketResponses)
                    .toList();

            return new PageResponse<>(allTicketResponses,page,size,tickets.getTotalElements(),tickets.getTotalPages(),tickets.isLast());
        }else{

            Page<Ticket> tickets=ticketRepository.findByCustomer_IdAndTicketStatus(customer.getId(),TicketStatus.valueOf(status),pageable);

            List<AllTicketResponse>allTicketResponses= tickets
                    .stream()
                    .map(ticketMapper::allTicketResponses)
                    .toList();

            return new PageResponse<>(allTicketResponses,page,size,tickets.getTotalElements(),tickets.getTotalPages(),tickets.isLast());
        }

    }
}
