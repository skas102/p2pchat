package dtos;

public class FriendRequestMessage implements Message {
    private String sender;

    public FriendRequestMessage(String sender) {
        this.sender = sender;
    }

    @Override
    public MessageType getType() {
        return MessageType.FRIEND_REQUEST;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }
}
