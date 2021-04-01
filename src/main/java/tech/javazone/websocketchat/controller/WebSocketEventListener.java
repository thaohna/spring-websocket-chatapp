package tech.javazone.websocketchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import tech.javazone.websocketchat.model.ChatMessage;
import tech.javazone.websocketchat.model.MessageType;

public class WebSocketEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        LOGGER.info("Connected");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
         StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
         String username = (String) headerAccessor.getSessionAttributes().get("username");

         if (username != null) {
             LOGGER.info("User Disconnected : " + username);

             ChatMessage chatMessage = ChatMessage.builder()
                     .messageType(MessageType.DISCONNECT)
                     .sender(username)
                     .build();
             sendingOperations.convertAndSend("/topic/public", chatMessage);
         }
    }
}
