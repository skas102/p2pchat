package messages;

import java.util.UUID;

public class GroupLeaveMessage implements Message {
    private String sender;
    private UUID groupKey;

    public GroupLeaveMessage(String sender, UUID groupKey) {
        this.sender = sender;
        this.groupKey = groupKey;
    }

    @Override
    public MessageType getType() {
        return MessageType.GROUP_LEAVE;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }

    public UUID getGroupKey() { return groupKey; }
}
