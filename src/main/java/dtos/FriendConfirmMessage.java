package dtos;

public class FriendConfirmMessage implements Message {
    private String sender;

    public FriendConfirmMessage(String sender) {
        this.sender = sender;
    }

    @Override
    public MessageType getType() {
        return MessageType.FRIEND_CONFIRM;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }
}
