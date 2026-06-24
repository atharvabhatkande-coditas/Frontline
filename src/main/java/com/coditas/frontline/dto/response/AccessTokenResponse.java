package com.coditas.frontline.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenResponse {
    private String accessToken;
}