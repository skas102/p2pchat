package dtos;

import java.util.UUID;

public class GroupInvitationMessage implements Message {
    private String sender;
    private UUID groupKey;

    public GroupInvitationMessage(String sender, UUID groupKey) {
        this.sender = sender;
        this.groupKey = groupKey;
    }

    @Override
    public MessageType getType() {
        return MessageType.GROUP_INVITATION;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }

    public UUID getGroupKey() {
        return groupKey;
    }
}
