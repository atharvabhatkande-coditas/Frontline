package com.coditas.frontline.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentDto {

    private String fileName;

    private String contentType;

    private String base64;

}
