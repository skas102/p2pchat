package dtos;

import java.util.UUID;

public class GroupJoinMessage implements Message {

    private String sender;
    private UUID groupKey;
    private PersonDTO joiner;

    public GroupJoinMessage(String sender, UUID groupKey, PersonDTO joiner) {
        this.sender = sender;
        this.groupKey = groupKey;
        this.joiner = joiner;
    }

    @Override
    public MessageType getType() {
        return MessageType.GROUP_JOIN;
    }

    @Override
    public String getSenderUsername() {
        return sender;
    }

    public UUID getGroupKey() { return groupKey; }

    public PersonDTO getJoiner() { return joiner; }
}
