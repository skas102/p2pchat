package models;

import java.util.ArrayList;
import java.util.List;

public class NotaryChat {
    private Person friend;
    private List<NotaryMessage> notaryMessages;

    public NotaryChat(Person friend) {
        this.friend = friend;
        this.notaryMessages = new ArrayList<>();
    }

    public Person getFriend() {
        return friend;
    }

    public void addNotaryMessage(NotaryMessage m) {
        notaryMessages.add(m);
    }

    public List<NotaryMessage> getNotaryMessages() {
        return notaryMessages;
    }
}
