package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.ChatOpenRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
