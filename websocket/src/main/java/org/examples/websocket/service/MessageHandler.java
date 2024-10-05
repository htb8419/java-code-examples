package org.examples.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examples.websocket.dto.NotificationPayloadDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {
    final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public MessageHandler(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    public void handle(NotificationPayloadDto notificationPayloadDto) throws Exception {
        messagingTemplate.convertAndSend("/icon/notify", objectMapper.writeValueAsString(notificationPayloadDto));
    }
}
