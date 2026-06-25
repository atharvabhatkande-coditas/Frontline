package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.TicketAssignRequest;
import com.coditas.frontline.dto.response.AgentAssignedTaskResponse;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.service.TicketAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assign")
public class TicketAssignmentController {
    private final TicketAssignmentService ticketAssignmentService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>assignAgentToTicket(@Valid @RequestBody TicketAssignRequest ticketAssignRequest, @AuthenticationPrincipal Users assignedBy){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketAssignmentService.assignTicketToAgent(ticketAssignRequest,assignedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @GetMapping("/agent")
    public ResponseEntity<ApplicationResponse<PageResponse<AgentAssignedTaskResponse>>>getAssignedTasks(
            @AuthenticationPrincipal Users agent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String name,
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        ApplicationResponse<PageResponse<AgentAssignedTaskResponse>>applicationResponse=new ApplicationResponse<>(ticketAssignmentService.getAssignedTasks(agent,page,size,name,sortDirection));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @PostMapping("/escalate")
    public ResponseEntity<ApplicationResponse<SingleResponse>>reAssignTicket(@Valid @RequestBody TicketAssignRequest ticketAssignRequest, @AuthenticationPrincipal Users assignedBy){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketAssignmentService.reAssignTicket(ticketAssignRequest,assignedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }


}
