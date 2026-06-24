package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL)
    private String username;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Size(min = 6,message = PASSWORD_SIZE)
    private String password;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String code;


}
