package com.coditas.frontline.mapper;

import com.coditas.frontline.dto.response.CustomerChatHistoryResponse;
import com.coditas.frontline.entity.History;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public CustomerChatHistoryResponse customerChatHistoryResponse(History history){
        return CustomerChatHistoryResponse.builder()
                .time(history.getCreatedAt())
                .role(history.getRole())
                .message(history.getMessage())
                .build();
    }
}
