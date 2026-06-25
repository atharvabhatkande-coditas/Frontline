package com.coditas.frontline.service;

import com.coditas.frontline.events.TicketCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    public void publishTicketCreated(TicketCreatedEvent event) {
        simpMessagingTemplate.convertAndSend(
                "/topic/manager",
                event
        );
    }

}
