package repositories;

import dtos.ChatMessage;
import models.Group;
import models.Person;

import java.io.Serializable;
import java.util.*;


public class MessageRepository implements Serializable {

    private Map<String, List<ChatMessage>> friendMessages;
    private Map<UUID, List<ChatMessage>> groupMessages;

    private List<ChatMessageListener> listeners;

    public MessageRepository() {
        this.friendMessages = new HashMap<>();
        this.groupMessages = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void registerListener(ChatMessageListener l) { this.listeners.add(l); }

    public void unregisterListener(ChatMessageListener l) { this.listeners.remove(l); }

    public void addGroupMessage(Group g, ChatMessage m) {
        if (groupMessages.containsKey(g.getUniqueId())) {
            groupMessages.get(g.getUniqueId()).add(m);
        } else {
            List<ChatMessage> messageList = new ArrayList<>();
            messageList.add(m);
            groupMessages.put(g.getUniqueId(), messageList);
        }
        notifyListeners();
    }

    public void addFriendMessage(Person p, ChatMessage m) {
        if (friendMessages.containsKey(p.getName())) {
            friendMessages.get(p.getName()).add(m);
        } else {
            List<ChatMessage> messageList = new ArrayList<>();
            messageList.add(m);
            friendMessages.put(p.getName(), messageList);
        }
        notifyListeners();
    }

    private void notifyListeners(){
        this.listeners.forEach(l -> l.onMessageReceived());
    }

}
