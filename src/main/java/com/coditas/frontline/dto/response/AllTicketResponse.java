package com.coditas.frontline.dto.response;

import com.coditas.frontline.enums.TicketStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllTicketResponse {

    private String ticketNumber;
    private String subject;
    private String description;
    private TicketStatus ticketStatus;
}
