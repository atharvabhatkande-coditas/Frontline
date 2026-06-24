package com.coditas.frontline.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseTokens {

    private String accessToken;
    private String refreshToken;
}
