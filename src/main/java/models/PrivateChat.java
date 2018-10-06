package models;

import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private Person person;
    private List<ChatMessage> privateMessges;

    public PrivateChat(Person person){
        this.person = person;
        this.privateMessges = new ArrayList<>();
    }

    public void addPrivateMessage(ChatMessage m){
        privateMessges.add(m);
    }
}
