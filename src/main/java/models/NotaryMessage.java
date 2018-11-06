package models;

import dtos.ChatMessageDTO;
import util.StringUtil;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class NotaryMessage extends ChatMessage {
    private NotaryState state;

    public NotaryMessage(String sender, String message) {
        this(sender, message, LocalDateTime.now());
    }

    public NotaryMessage(String sender, String message, LocalDateTime sentDateTime) {
        super(sender, message, sentDateTime);
        state = NotaryState.PENDING;
    }

    public static NotaryMessage create(ChatMessageDTO dto) {
        return new NotaryMessage(dto.getSender(), dto.getMessage(), dto.getSentDateTime());
    }

    public NotaryState getState() {
        return state;
    }

    public void setState(NotaryState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s: %s",
                getFormattedDateTime(), state.toString(), getSender(), getMessage());
    }

    public byte[] getHash() throws NoSuchAlgorithmException {
        return StringUtil.sha256(String.format("%s %s %s", getFormattedDateTime(), getSender(), getMessage()));
    }
}
