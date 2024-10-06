package org.examples.websocket.service;

import org.examples.websocket.dto.NotificationPayloadDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MessageHandler {
    final SimpMessagingTemplate messagingTemplate;


    public MessageHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void handle(NotificationPayloadDto notificationPayloadDto) {
        if (StringUtils.hasText(notificationPayloadDto.sid())) {
            messagingTemplate.convertAndSendToUser(notificationPayloadDto.sid(), "/queue/notify", notificationPayloadDto);
        } else {
            messagingTemplate.convertAndSend("/topic/notify", notificationPayloadDto);
        }

    }
}
