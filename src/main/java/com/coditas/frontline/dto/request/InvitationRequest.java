package com.coditas.frontline.dto.request;


import com.coditas.frontline.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.frontline.constants.ValidationConstants.EMAIL;
import static com.coditas.frontline.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationRequest {
    @Email(message = EMAIL)
    private String email;
    @NotNull(message = NOT_NULL)
    private RoleType role;

}