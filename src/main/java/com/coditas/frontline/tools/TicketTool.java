package com.coditas.frontline.tools;

import com.coditas.frontline.dto.response.AllTicketResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.service.TicketAssignmentService;
import com.coditas.frontline.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketTool {

    private final TicketService ticketService;
    private final  TicketAssignmentService ticketAssignmentService;

    @Tool(name = "getCustomerHistory",description = "This will return all the customer ticket history")
    public List<AllTicketResponse> getCustomerHistory(Long customerId){
        return ticketService.getCustomerHistory(customerId);
    }

    @Tool(name = "reAssignAndUpdatePriority",description = "This will reassign to the billing team and update the priority")
    public SingleResponse reAssignAndUpdatePriority(String ticketNo ,Long billingTeamAgentId){
        return ticketAssignmentService.reAssignAndUpdatePriority(ticketNo,billingTeamAgentId);
    }






}
