package dtos;

import messages.Message;
import messages.MessageType;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private String sender;
    private String message;
    private LocalDateTime sendDateTime;

    public ChatMessageDTO(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.sendDateTime = LocalDateTime.now();
    }

    public String getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }
}
