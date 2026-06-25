package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.InvitationRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.InvitationResponse;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invite")
public class InvitationController {
    private final InvitationService invitationService;
    @PostMapping
    public ResponseEntity<ApplicationResponse<InvitationResponse>> sendInvitation(@Valid @RequestBody InvitationRequest invitationRequest, @AuthenticationPrincipal Users invitedBy){
        ApplicationResponse<InvitationResponse>applicationResponse=new ApplicationResponse<>(invitationService.sendInvitation(invitationRequest,invitedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }


}
