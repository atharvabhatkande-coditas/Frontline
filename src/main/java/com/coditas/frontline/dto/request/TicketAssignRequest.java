package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketAssignRequest{
    @NotNull(message = NOT_NULL)
    private Long agentId;
    @NotNull(message = NOT_NULL)
    private String ticketNo;
}
