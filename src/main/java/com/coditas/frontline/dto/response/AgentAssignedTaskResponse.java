package com.coditas.frontline.dto.response;

import com.coditas.frontline.enums.TicketStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentAssignedTaskResponse {

    private String ticketNo;
    private TicketStatus status;

}
