package com.coditas.frontline.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatOpenRequest {

    private String ticketNo;
    private String message;
}
