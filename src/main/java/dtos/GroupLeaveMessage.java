package dtos;

public class GroupLeaveMessage implements Message {
    private String sender;
    private String groupKey;

    public GroupLeaveMessage(String sender, String groupKey) {
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

    public String getGroupKey() { return groupKey; }
}
