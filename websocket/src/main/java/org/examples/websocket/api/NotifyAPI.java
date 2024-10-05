package org.examples.websocket.api;

import org.examples.websocket.dto.NotificationPayloadDto;
import org.examples.websocket.service.MessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyAPI {

    final MessageHandler messageHandler;

    public NotifyAPI(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @MessageMapping("/push")
    public void pushMessage(@Payload Message<NotificationPayloadDto> message) throws Exception {
        messageHandler.handle(message.getPayload());
    }
}
