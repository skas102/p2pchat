package messages;

import dtos.ChatMessageDTO;

public class NewNotaryChatMessage implements Message {
    private ChatMessageDTO messageDTO;

    private String recipientIdentifier;

    public NewNotaryChatMessage(String recipientIdentifier, ChatMessageDTO messageDTO) {
        this.recipientIdentifier = recipientIdentifier;
        this.messageDTO = messageDTO;
    }

    @Override
    public MessageType getType() {
        return MessageType.NEW_NOTARY_CHAT_MESSAGE;
    }

    @Override
    public String getSenderUsername() {
        return messageDTO.getSender();
    }

    public ChatMessageDTO getMessageDTO() {
        return messageDTO;
    }

    public String getRecipientIdentifier() {
        return recipientIdentifier;
    }
}
