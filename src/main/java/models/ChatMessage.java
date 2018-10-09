package models;

import dtos.ChatMessageDTO;
import util.StringUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    private String sender;
    private String message;
    private LocalDateTime sentDateTime;

    public ChatMessage(String sender, String message) {
        this(sender, message, LocalDateTime.now());
    }

    public ChatMessage(String sender, String message, LocalDateTime sentDateTime) {
        this.sender = sender;
        this.message = message;
        this.sentDateTime = sentDateTime;
    }

    public static ChatMessage create(ChatMessageDTO dto) {
        return new ChatMessage(dto.getSender(), dto.getMessage(), dto.getSentDateTime());
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

    public String getFormattedDateTime(){
        return StringUtil.formatDateTime(sentDateTime);
    }

    public ChatMessageDTO createDTO() {
        return new ChatMessageDTO(sender, message, sentDateTime);
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s",
                getFormattedDateTime(), sender, message);
    }
}
