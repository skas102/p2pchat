package dtos;

public class GroupInvitationMessage implements Message {
    private String sender;
    private String groupKey;

    public GroupInvitationMessage(String sender, String groupKey) {
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

    public String getGroupKey() {
        return groupKey;
    }
}
