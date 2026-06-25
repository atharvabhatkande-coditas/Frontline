package com.coditas.frontline.controller;

import com.coditas.frontline.dto.response.AgentResponse;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/agents")
    public ResponseEntity<ApplicationResponse<PageResponse<AgentResponse>>>getAllAgents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String name,
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        ApplicationResponse<PageResponse<AgentResponse>>applicationResponse=new ApplicationResponse<>(userService.getAllAgents(page,size,name,sortDirection));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
}
