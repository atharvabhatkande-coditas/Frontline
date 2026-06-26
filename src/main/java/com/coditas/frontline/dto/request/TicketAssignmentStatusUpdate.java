package com.coditas.frontline.dto.request;

import com.coditas.frontline.enums.TicketAssignmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketAssignmentStatusUpdate {

    @NotNull(message = NOT_NULL)
    private Long ticketAssignmentId;
    @NotNull(message = NOT_NULL)
    private TicketAssignmentStatus ticketAssignmentStatus;
}
