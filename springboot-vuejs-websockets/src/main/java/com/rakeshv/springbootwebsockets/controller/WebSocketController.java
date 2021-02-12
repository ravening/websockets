package com.rakeshv.springbootwebsockets.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.springbootwebsockets.models.Greeting;
import com.rakeshv.springbootwebsockets.models.ModelInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessageSendingOperations template;

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpMessagingTemplate messageTemplate;
    private final ModelInfoHolder modelInfoHolder;

    public WebSocketController(SimpMessagingTemplate template, ModelInfoHolder holder) {
        this.messageTemplate = template;
        this.modelInfoHolder = holder;
    }

    @MessageMapping("/news")
    @SendTo("/topic/news")
    public String broadcastNews(@Payload String message) {
        return message;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendWebSocketUpdate() throws JsonProcessingException {
        modelInfoHolder.changeValues();
        this.messageTemplate.convertAndSend("/info/values",
                mapper.writeValueAsString(modelInfoHolder.getModelInfoList()));
    }

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        return mapper.readValue(message, Map.class).get("name").toString();
    }
}
