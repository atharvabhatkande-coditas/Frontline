package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.TicketAssignRequest;
import com.coditas.frontline.dto.response.AgentAssignedTaskResponse;
import com.coditas.frontline.dto.response.ErrorResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Ticket;
import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.TicketAssignmentStatus;
import com.coditas.frontline.exception.AlreadyExistException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.*;
import static com.coditas.frontline.enums.RoleType.AGENT;

@Service
@RequiredArgsConstructor
public class TicketAssignmentService {
    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final CustomUsersRepository customUsersRepository;
    private final TicketRepository ticketRepository;

    private final UserMapper userMapper;

    @Transactional
    public SingleResponse assignTicketToAgent(@Valid TicketAssignRequest ticketAssignRequest, Users assignedBy) {
        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndAgent_Id(ticketAssignRequest.getTicketNo(),ticketAssignRequest.getAgentId())
                .orElse(null);

        if(!Objects.isNull(ticketAssignment)){
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

        ticketAssignmentRepository.save(newTicketAssignment);

        return SingleResponse.builder()
                .message(TICKET_ASSIGNED)
                .build();
    }

    public PageResponse<AgentAssignedTaskResponse> getAssignedTasks(Users agent, int page, int size, String name, String sortDirection) {

        Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ?Sort.by(name).ascending()
                : Sort.by(name).descending();
        Pageable pageable= PageRequest.of(page,size,sort);

        Page<TicketAssignment> ticketAssignmentPage=ticketAssignmentRepository.findByAgent(agent,pageable);

        List<AgentAssignedTaskResponse>assignedTaskResponses= ticketAssignmentPage.stream().map(userMapper::assignedTaskResponse).toList();

        return new PageResponse<>(assignedTaskResponses,page,size,ticketAssignmentPage.getTotalElements(),ticketAssignmentPage.getTotalPages(),ticketAssignmentPage.isLast());
    }
}
