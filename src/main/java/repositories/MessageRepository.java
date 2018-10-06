package repositories;

import models.*;

import java.io.Serializable;
import java.util.*;


public class MessageRepository implements Serializable {

    private Map<String, PrivateChat> friendMessages;
    private Map<UUID, GroupChat> groupMessages;

    private List<ChatMessageListener> listeners;

    public MessageRepository() {
        this.friendMessages = new HashMap<>();
        this.groupMessages = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void registerListener(ChatMessageListener l) {
        this.listeners.add(l);
    }

    public void unregisterListener(ChatMessageListener l) {
        this.listeners.remove(l);
    }

    public void addGroupMessage(Group g, ChatMessage m) {
        if (groupMessages.containsKey(g.getUniqueId())) {
            groupMessages.get(g.getUniqueId()).addMessage(m);
        } else {
            GroupChat groupChat = new GroupChat(g);
            groupChat.addMessage(m);
            groupMessages.put(g.getUniqueId(), groupChat);
        }
        notifyListeners();
    }

    public void addFriendMessage(Person p, ChatMessage m) {
        if (friendMessages.containsKey(p.getName())) {
            friendMessages.get(p.getName()).addPrivateMessage(m);
        } else {
            PrivateChat privateChat = new PrivateChat(p);
            privateChat.addPrivateMessage(m);
            friendMessages.put(p.getName(), privateChat);
        }
        notifyListeners();
    }

    private void notifyListeners() {
        this.listeners.forEach(l -> l.onMessageReceived());
    }

}
