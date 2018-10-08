package messages;

public class FriendRemovalMessage implements Message {

    private String sender;

    public FriendRemovalMessage(String sender) {
        this.sender = sender;
    }

    @Override
    public MessageType getType() {
        return MessageType.FRIEND_REMOVAL;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }
}
