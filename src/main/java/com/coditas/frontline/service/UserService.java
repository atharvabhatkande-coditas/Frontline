package com.coditas.frontline.service;

import com.coditas.frontline.dto.response.AgentResponse;
import com.coditas.frontline.dto.response.ErrorResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.RoleType;
import com.coditas.frontline.mapper.UserMapper;
import com.coditas.frontline.repository.CustomUsersRepository;
import com.coditas.frontline.repository.TicketAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final CustomUsersRepository customUsersRepository;
    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final UserMapper userMapper;


    public PageResponse<AgentResponse>getAllAgents(int page, int size, String name, String sortDirection) {
        Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ?Sort.by(name).ascending()
                : Sort.by(name).descending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<TicketAssignment> ticketAssignments=ticketAssignmentRepository.findAll(pageable);

        List<AgentResponse>agentResponses=userMapper.agentResponseList(ticketAssignments.getContent());
        return new PageResponse<>(agentResponses,page,size,ticketAssignments.getTotalElements(),ticketAssignments.getTotalPages(),ticketAssignments.isLast());
    }
}
