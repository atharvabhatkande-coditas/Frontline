package com.coditas.frontline.controller;

import com.coditas.frontline.dto.request.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/demo")
    public MessageEvent send(MessageEvent message) {

        log.info("Received message: {}", message.getMessage());

        return new MessageEvent(
                "Server Received -> " + message.getMessage()
        );
    }
}
