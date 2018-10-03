package dtos;

public class GroupJoinMessage implements Message {

    private String sender;
    private String groupKey;
    private PersonDTO joiner;

    public GroupJoinMessage(String sender, String groupKey, PersonDTO joiner) {
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

    public String getGroupKey() { return groupKey; }

    public PersonDTO getJoiner() { return joiner; }
}
