package com.coditas.frontline.dto.request;

import com.coditas.frontline.enums.Priority;

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
public class OpenTicketRequest {
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String description;
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String subject;

}
