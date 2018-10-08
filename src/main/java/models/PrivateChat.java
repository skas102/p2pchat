package models;

import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private Person friend;
    private List<ChatMessage> privateMessges;

    public PrivateChat(Person friend) {
        this.friend = friend;
        this.privateMessges = new ArrayList<>();
    }

    public Person getFriend() {
        return friend;
    }

    public void addPrivateMessage(ChatMessage m) {
        privateMessges.add(m);
    }

    public List<ChatMessage> getPrivateMessges() {
        return privateMessges;
    }
}
