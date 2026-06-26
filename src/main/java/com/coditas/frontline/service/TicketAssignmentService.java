package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.EmailRequest;
import com.coditas.frontline.dto.request.TicketAssignRequest;
import com.coditas.frontline.dto.response.AgentAssignedTaskResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Ticket;
import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.Priority;
import com.coditas.frontline.enums.RoleType;
import com.coditas.frontline.enums.TicketAssignmentStatus;
import com.coditas.frontline.exception.AlreadyExistException;
import com.coditas.frontline.exception.AuthorizationException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.mapper.UserMapper;
import com.coditas.frontline.repository.CustomUsersRepository;
import com.coditas.frontline.repository.TicketAssignmentRepository;
import com.coditas.frontline.repository.TicketRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.frontline.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.*;
import static com.coditas.frontline.enums.RoleType.AGENT;

@Service
@RequiredArgsConstructor
public class TicketAssignmentService {
    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final CustomUsersRepository customUsersRepository;
    private final TicketRepository ticketRepository;
    private final EmailService emailService;

    private final UserMapper userMapper;

    @Transactional
    public SingleResponse assignTicketToAgent(@Valid TicketAssignRequest ticketAssignRequest, Users assignedBy) {
        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndAgent_Id(ticketAssignRequest.getTicketNo(),ticketAssignRequest.getAgentId())
                .orElse(null);

        if(!Objects.isNull(ticketAssignment)){
            throw new AlreadyExistException(TICKET_ALREADY_ASSIGNED);
        }

        TicketAssignment lastAssigned=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(ticketAssignRequest.getTicketNo(),true)
                .orElse(null);
        if(!Objects.isNull(lastAssigned)){
            throw new AlreadyExistException(TICKET_ALREADY_ASSIGNED);
        }

        Users agent=customUsersRepository.findById(ticketAssignRequest.getAgentId())
                .orElseThrow(()->new NotFoundException(AGENT+NOT_FOUND));

        Ticket ticket=ticketRepository.findByTicketNo(ticketAssignRequest.getTicketNo())
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));

        TicketAssignment newTicketAssignment=TicketAssignment.builder()
                .agent(agent)
                .ticket(ticket)
                .ticketAssignmentStatus(TicketAssignmentStatus.ACTIVE)
                .assignedBy(assignedBy)
                .isCurrentAgent(true)
                .build();
        EmailRequest emailRequest=EmailRequest.builder()
                .message(AGENT_ASSIGNED_MAIL)
                .email(ticket.getCustomer().getUsername())
                .build();
        String message=emailService.sendEmail(emailRequest);
        ticketAssignmentRepository.save(newTicketAssignment);


        return SingleResponse.builder()
                .message(TICKET_ASSIGNED+message)
                .build();
    }

    public PageResponse<AgentAssignedTaskResponse> getAssignedTasks(Users agent, int page, int size, String name, String sortDirection,Long agentId) {

       if(!Objects.equals(agent.getRole().name(), RoleType.MANAGER.name()) && !Objects.equals(agent.getId(),agentId)){
            throw new AuthorizationException(UNAUTHORIZED);
            }


        Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ?Sort.by(name).ascending()
                : Sort.by(name).descending();
        Pageable pageable= PageRequest.of(page,size,sort);

        Page<TicketAssignment> ticketAssignmentPage=ticketAssignmentRepository.findByAgent_Id(agentId,pageable);

        List<AgentAssignedTaskResponse>assignedTaskResponses= ticketAssignmentPage.stream().map(userMapper::assignedTaskResponse).toList();

        return new PageResponse<>(assignedTaskResponses,page,size,ticketAssignmentPage.getTotalElements(),ticketAssignmentPage.getTotalPages(),ticketAssignmentPage.isLast());
    }
    @Transactional
    public SingleResponse reAssignTicket(TicketAssignRequest ticketAssignRequest, Users assignedBy) {

        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(ticketAssignRequest.getTicketNo(),true)
                .orElseThrow(()->new NotFoundException(TICKET_NOT_ASSIGNED));

        ticketAssignment.setCurrentAgent(false);
        ticketAssignmentRepository.save(ticketAssignment);
       return  assignTicketToAgent(ticketAssignRequest,assignedBy);

    }
    @Transactional
    public SingleResponse reAssignAndUpdatePriority(String ticketNo,Long billingTeamAgentId) {
        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(ticketNo,true)
                .orElseThrow(()->new NotFoundException(TICKET_NOT_ASSIGNED));

        Ticket ticket=ticketRepository.findByTicketNo(ticketNo)
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));

        ticketAssignment.setCurrentAgent(false);
        ticketAssignmentRepository.save(ticketAssignment);
        ticket.setPriority(Priority.HIGH);
        ticketRepository.save(ticket);
        TicketAssignRequest ticketAssignRequest=TicketAssignRequest.builder()
                .agentId(billingTeamAgentId)
                .ticketNo(ticketNo)
                .build();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users assignedBy=(Users) authentication.getPrincipal();
        return assignTicketToAgent(ticketAssignRequest,assignedBy);
    }
}
