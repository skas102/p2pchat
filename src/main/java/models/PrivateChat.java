package models;

import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private Person friend;
    private List<ChatMessage> privateMessages;
    private List<NotaryMessage> notaryMessages;

    public PrivateChat(Person friend) {
        this.friend = friend;
        this.privateMessages = new ArrayList<>();
        this.notaryMessages = new ArrayList<>();
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

    public void addNotaryMessage(NotaryMessage m) {
        notaryMessages.add(m);
    }

    public List<NotaryMessage> getNotaryMessages() {
        return notaryMessages;
    }
}
