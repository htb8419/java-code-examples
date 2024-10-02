package org.examples.websocket.api;

import com.example.chat.model.ClientError;
import com.example.chat.model.messages.ClientInstantMessage;
import com.example.chat.model.messages.MessageWrapper;
import com.example.chat.repo.ClientErrorRepo;
import com.example.chat.service.ChatMessageHandler;
import com.example.chat.service.EventMessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NotifyAPI {


    @MessageMapping("/push")
    public void pushMessage(Message<String> message) {
        messageHandler.handleMessage(MessageWrapper.newInstance(message));
    }
}
