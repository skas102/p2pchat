package models;

import java.util.ArrayList;
import java.util.List;

public class NotaryChat {
    private Person friend;
    private List<ChatMessage> notaryMessages;

    public NotaryChat(Person friend) {
        this.friend = friend;
        this.notaryMessages = new ArrayList<>();
    }

    public Person getFriend() {
        return friend;
    }

    public void addNotaryMessage(ChatMessage m) {
        notaryMessages.add(m);
    }

    public List<ChatMessage> getNotaryMessages() {
        return notaryMessages;
    }
}
