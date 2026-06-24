package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.LoginRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserAuthController {

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<SingleResponse>>loginUser(@Valid @RequestBody LoginRequest loginRequest){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(authService.loginPlatformUser(request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);

    }
}
