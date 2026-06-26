package com.coditas.frontline.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerChatHistoryResponse {

    private LocalDateTime time;
    private String role;
    private String message;
}
