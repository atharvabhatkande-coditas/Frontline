package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.CustomerHistoryRequest;
import com.coditas.frontline.dto.response.CopilotResponse;
import com.coditas.frontline.tools.TicketTool;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static com.coditas.frontline.constants.AuthConstants.PROMPT;

@Service
@RequiredArgsConstructor
public class CopilotService {
    private final TicketService ticketService;
    private final ChatClient chatClient;
    private final TicketTool ticketTool;

    public CopilotResponse chatWithCopilot(@Valid CustomerHistoryRequest customerHistoryRequest, int page, int size, String name, String sortDirection) {

        String response=chatClient.prompt()
                .system(PROMPT)
                .user(customerHistoryRequest.getMessage())
                .tools(ticketTool)
                .call()
                .content();

        return CopilotResponse.builder()
                .message(response)
                .build();

    }
}
