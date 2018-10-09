package models;

import java.util.ArrayList;
import java.util.List;

public class GroupChat {
    private Group group;
    private List<ChatMessage> messages;

    public GroupChat(Group g) {
        this.group = g;
        this.messages = new ArrayList<>();
    }

    public Group getGroup() {
        return group;
    }

    public void addMessage(ChatMessage m) {
        messages.add(m);
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }
}
