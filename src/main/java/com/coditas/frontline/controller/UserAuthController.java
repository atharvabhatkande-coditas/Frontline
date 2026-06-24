package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.LoginRequest;
import com.coditas.frontline.dto.request.RegisterRequest;
import com.coditas.frontline.dto.response.AccessTokenResponse;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.LoginResponseTokens;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>>loginUser(@Valid @RequestBody LoginRequest loginRequest){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(userAuthService.loginPlatformUser(loginRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<SingleResponse>>registerNewPlatformUser(@RequestBody RegisterRequest registerRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(userAuthService.registerPlatformUser(registerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }
    @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<SingleResponse>>logoutUser(@AuthenticationPrincipal Users user,@RequestParam String refreshToken){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(userAuthService.logoutUser(user,refreshToken));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<AccessTokenResponse>>generateAccessToken(@RequestParam String refreshToken, @AuthenticationPrincipal Users user){
        ApplicationResponse<AccessTokenResponse>applicationResponse=new ApplicationResponse<>(userAuthService.generateAccessToken(refreshToken,user));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
