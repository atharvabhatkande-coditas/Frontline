package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.FeedbackRequest;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.CustomerFeedBack;
import com.coditas.frontline.entity.Ticket;
import com.coditas.frontline.enums.TicketStatus;
import com.coditas.frontline.exception.ForbiddenException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.TicketRatingRepository;
import com.coditas.frontline.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.*;

@Service
@RequiredArgsConstructor
public class TicketRatingService {

    private final TicketRatingRepository ticketRatingRepository;
    private final TicketRepository ticketRepository;
    @Transactional
    public SingleResponse giveFeedback(FeedbackRequest feedbackRequest, String ticketNo) {
        Ticket ticket=ticketRepository.findByTicketNo(ticketNo)
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));

        if(!Objects.equals(ticket.getTicketStatus().name(), TicketStatus.RESOLVED.name())){
            throw new ForbiddenException(TICKET_NOT_RESOLVED);
        }
        CustomerFeedBack customerFeedBack=CustomerFeedBack.builder()
                .ticket(ticket)
                .rating(feedbackRequest.getRating())
                .build();

        if(!Objects.isNull(feedbackRequest.getDescription())){
            customerFeedBack.setDescription(feedbackRequest.getDescription());
        }

        ticketRatingRepository.save(customerFeedBack);
        return SingleResponse.builder()
                .message(FEEDBACK_RECEIVED)
                .build();
    }
}