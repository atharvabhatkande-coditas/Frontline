package com.coditas.frontline.dto.response;

import com.coditas.frontline.enums.TeamType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentResponse {

    private Long agentId;
    private String agentUsername;
    private TeamType team;
    private List<AgentAssignedTaskResponse> ticketsAssigned;
}
