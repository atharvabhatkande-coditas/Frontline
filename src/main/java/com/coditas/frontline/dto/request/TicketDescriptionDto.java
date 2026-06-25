package com.coditas.frontline.dto.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDescriptionDto {

    private String message;

    private List<AttachmentDto> attachments;

}