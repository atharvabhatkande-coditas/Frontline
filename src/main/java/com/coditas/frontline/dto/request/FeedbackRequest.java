package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackRequest {
    @NotNull(message = NOT_NULL)
    private Integer rating;
    private String description;
}
