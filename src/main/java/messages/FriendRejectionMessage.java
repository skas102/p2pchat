package messages;

public class FriendRejectionMessage implements Message {

    private String sender;

    public FriendRejectionMessage(String sender) {
        this.sender = sender;
    }

    @Override
    public MessageType getType() {
        return MessageType.FRIEND_REJECTION;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }
}
