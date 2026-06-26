package com.coditas.frontline.dto.response;

import com.coditas.frontline.enums.TicketStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseForDraft {

    private String subject;
    private String description;
    private TicketStatus status;
}
