package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackRequest {
    @NotNull(message = NOT_NULL)
    @Max(value = 5,message =MAX)
    @Min(value = 1,message = MIN)
    private Integer rating;
    private String description;
}
