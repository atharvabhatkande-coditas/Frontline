package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.ChatMessageRequest;
import com.coditas.frontline.dto.request.ChatOpenRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.CustomerChatHistoryResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>openChat(@Valid @RequestBody ChatOpenRequest chatOpenRequest, @AuthenticationPrincipal UserDetails userDetails){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(chatService.openChat(chatOpenRequest,userDetails));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @PostMapping("/message")
    public ResponseEntity<ApplicationResponse<SingleResponse>>message(@Valid @RequestBody ChatMessageRequest chatMessageRequest, @AuthenticationPrincipal UserDetails userDetails){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(chatService.message(chatMessageRequest,userDetails));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @GetMapping("/history/customer/{customerId}")
    public ResponseEntity<ApplicationResponse<PageResponse<CustomerChatHistoryResponse>>>getCustomerChatHistory(
            @RequestParam String ticketNo,
            @PathVariable Long customerId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String name,
            @RequestParam(defaultValue = "desc") String sortDirection

            ){
        ApplicationResponse<PageResponse<CustomerChatHistoryResponse>>applicationResponse=new ApplicationResponse<>(chatService.getCustomerChatHistory(ticketNo,customerId,userDetails,page,size,name,sortDirection));

        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }
}
