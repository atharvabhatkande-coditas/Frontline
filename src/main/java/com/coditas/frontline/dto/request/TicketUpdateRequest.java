package com.coditas.frontline.dto.request;

import com.coditas.frontline.enums.Priority;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketUpdateRequest {
    @NotNull(message = NOT_NULL)
    private String ticketNo;
    @NotNull(message = NOT_NULL)
    private Priority priority;
}
