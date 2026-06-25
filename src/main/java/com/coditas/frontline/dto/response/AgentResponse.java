package com.coditas.frontline.dto.response;

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
    private List<AgentAssignedTaskResponse> ticketsAssigned;
}
