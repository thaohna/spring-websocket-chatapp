package tech.javazone.websocketchat.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ChatMessage {
    @Getter
    private MessageType messageType;
    @Getter
    private String content;
    @Getter
    private String sender;
    @Getter
    private String time;
}
