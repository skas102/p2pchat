package dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessageDTO implements Serializable {
    private String sender;
    private String message;
    private LocalDateTime sentDateTime;

    public ChatMessageDTO(String sender, String message, LocalDateTime sentDateTime) {
        this.sender = sender;
        this.message = message;
        this.sentDateTime = sentDateTime;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }
}
