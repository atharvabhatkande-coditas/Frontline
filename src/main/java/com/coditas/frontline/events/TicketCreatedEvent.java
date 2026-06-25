package com.coditas.frontline.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCreatedEvent {
    private Long ticketId;
    private String ticketNumber;
    private String subject;
    private String priority;
    private String status;
}
