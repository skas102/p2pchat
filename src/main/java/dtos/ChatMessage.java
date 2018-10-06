package dtos;

import messages.Message;
import messages.MessageType;
import models.ContactType;

import java.time.LocalDateTime;

public class ChatMessage implements Message {

    private String sender;
    private String message;
    // Identifies the contact the message has been sent to (either user name or group name)
    private String contactIdentifier;
    private ContactType contactType;
    private LocalDateTime sendDateTime;

    public ChatMessage(String sender, String contactIdentifier, ContactType contactType, String message) {
        this.sender = sender;
        this.message = message;
        this.contactIdentifier = contactIdentifier;
        this.contactType = contactType;
        this.sendDateTime = LocalDateTime.now();
    }

    @Override
    public MessageType getType() {
        return MessageType.CHAT_MESSAGE;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getContactIdentifier() {
        return contactIdentifier;
    }

    public ContactType getContactType() { return contactType; }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }
}
