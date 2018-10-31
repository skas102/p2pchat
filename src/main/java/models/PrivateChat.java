package models;

import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private Person friend;
    private List<ChatMessage> privateMessages;

    public PrivateChat(Person friend) {
        this.friend = friend;
        this.privateMessages = new ArrayList<>();
    }

    public Person getFriend() {
        return friend;
    }

    public void addPrivateMessage(ChatMessage m) {
        privateMessages.add(m);
    }

    public List<ChatMessage> getPrivateMessages() {
        return privateMessages;
    }
}
