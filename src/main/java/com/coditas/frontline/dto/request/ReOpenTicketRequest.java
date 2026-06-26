package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.NOT_BLANK;
import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReOpenTicketRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String ticketNo;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String subject;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String description;
}
