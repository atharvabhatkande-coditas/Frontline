package com.coditas.frontline.mapper;

import com.coditas.frontline.dto.response.AgentAssignedTaskResponse;
import com.coditas.frontline.dto.response.AgentResponse;
import com.coditas.frontline.entity.TicketAssignment;
import com.coditas.frontline.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public List<AgentResponse> agentResponseList(List<TicketAssignment>ticketAssignments){
        Map<Long,List<TicketAssignment>>listMap=ticketAssignments.stream().collect(Collectors.groupingBy(ticketAssignment -> ticketAssignment.getAgent().getId()));

        return listMap.values().stream().map(this::agentResponse).toList();
    }

    public AgentResponse agentResponse(List<TicketAssignment>ticketAssignments){
        Users agent=ticketAssignments.getFirst().getAgent();

        List<AgentAssignedTaskResponse>assignedTaskResponses=ticketAssignments.stream().map(this::assignedTaskResponse).toList();
        return AgentResponse.builder()
                .agentId(agent.getId())
                .agentUsername(agent.getUsername())
                .team(agent.getTeam())
                .ticketsAssigned(assignedTaskResponses)
                .build();
    }

    public AgentAssignedTaskResponse assignedTaskResponse(TicketAssignment ticketAssignment){
        return AgentAssignedTaskResponse.builder()
                .ticketNo(ticketAssignment.getTicket().getTicketNo())
                .status(ticketAssignment.getTicket().getTicketStatus())
                .build();
    }
}
