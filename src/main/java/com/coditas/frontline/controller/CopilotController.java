package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.CustomerHistoryRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.CopilotResponse;
import com.coditas.frontline.service.CopilotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/copilot")
public class CopilotController {
    private final CopilotService copilotService;

    @PostMapping("/chat")
    public ResponseEntity<ApplicationResponse<CopilotResponse>>chatWithCopilot(
            @Valid @RequestBody CustomerHistoryRequest customerHistoryRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String name,
            @RequestParam(defaultValue = "desc") String sortDirection

    ){

        ApplicationResponse<CopilotResponse>applicationResponse=new ApplicationResponse<>(copilotService.chatWithCopilot(customerHistoryRequest,page,size,name,sortDirection));

        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }
}
