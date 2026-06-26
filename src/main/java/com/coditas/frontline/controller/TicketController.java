package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.*;
import com.coditas.frontline.dto.response.*;
import com.coditas.frontline.entity.Customer;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("open-ticket")
    public ResponseEntity<ApplicationResponse<SingleResponse>>openTicket(@Valid @RequestBody OpenTicketRequest openTicketRequest, @AuthenticationPrincipal Customer customer){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketService.openTicket(openTicketRequest,customer));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @GetMapping
    public ResponseEntity<ApplicationResponse<TicketResponse>>getTicket(@AuthenticationPrincipal Customer customer, @RequestParam String ticketNo){
        ApplicationResponse<TicketResponse>applicationResponse=new ApplicationResponse<>(ticketService.getTicketDetails(customer,ticketNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }

    @GetMapping("/all")
    public ResponseEntity<ApplicationResponse<PageResponse<AllTicketResponse>>>getTicketByStatus(
            @AuthenticationPrincipal Customer customer,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String name,
            @RequestParam(defaultValue = "desc") String sortDirection

            ){
        ApplicationResponse<PageResponse<AllTicketResponse>>applicationResponse=new ApplicationResponse<>(ticketService.getAllTicket(customer,status,page,size,name,sortDirection));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }


    @PatchMapping("/set-priority")
    public ResponseEntity<ApplicationResponse<SingleResponse>>setPriority(@Valid @RequestBody TicketUpdateRequest ticketUpdateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketService.setPriority(ticketUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PostMapping("/re-open")
    public ResponseEntity<ApplicationResponse<SingleResponse>>reOpenTicket(@Valid @RequestBody ReOpenTicketRequest reOpenTicketRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketService.reOpenTicket(reOpenTicketRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<ApplicationResponse<SingleResponse>>updateStatus(@Valid @RequestBody TicketStatusUpdateRequest ticketStatusUpdateRequest, @AuthenticationPrincipal Users user){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketService.updateStatus(ticketStatusUpdateRequest,user));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }


    @PatchMapping("/resolve")
    public ResponseEntity<ApplicationResponse<SingleResponse>>resolveTicket(@Valid @RequestBody TicketResolveRequest ticketStatusUpdateRequest, @AuthenticationPrincipal Users user){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketService.resolveTicket(ticketStatusUpdateRequest,user));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }







}
