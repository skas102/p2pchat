package messages;

import dtos.ChatMessageDTO;
import models.ContactType;

public class NewChatMessage implements Message {
    private ChatMessageDTO messageDTO;

    private ContactType contactType;

    private String recipientIdentifier;
    public NewChatMessage(ContactType contactType, String recipientIdentifier, ChatMessageDTO messageDTO){
        this.contactType = contactType;
        this.recipientIdentifier = recipientIdentifier;
        this.messageDTO = messageDTO;
    }

    @Override
    public MessageType getType() {
        return MessageType.NEW_CHAT_MESSAGE;
    }

    @Override
    public String getSenderUsername() {
        return null;
    }

    public ChatMessageDTO getMessageDTO() {
        return messageDTO;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getRecipientIdentifier() {
        return recipientIdentifier;
    }
}
