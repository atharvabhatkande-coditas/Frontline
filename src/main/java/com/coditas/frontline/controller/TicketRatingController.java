package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.FeedbackRequest;
import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.service.TicketRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class TicketRatingController {

    private final TicketRatingService ticketRatingService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>giveFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest, @RequestParam String ticketNo){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(ticketRatingService.giveFeedback(feedbackRequest,ticketNo));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }
}
