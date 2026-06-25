package com.coditas.frontline.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.*;
import static com.coditas.frontline.constants.ValidationConstants.NOT_BLANK;
import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;
import static com.coditas.frontline.constants.ValidationConstants.PASSWORD_SIZE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegisterRequest {

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
    private String firstName;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String lastName;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Size(max = 10,message = PHONE_NUMBER)
    private String phoneNumber;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String code;
}
