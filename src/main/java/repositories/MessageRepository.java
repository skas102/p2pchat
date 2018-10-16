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

    public PrivateChat getPrivateChat(Person p) {
        if (friendMessages.containsKey(p.getName())) {
            return friendMessages.get(p.getName());
        }

        PrivateChat chat = new PrivateChat(p);
        friendMessages.put(p.getName(), chat);
        return chat;
    }

    public void addFriendMessage(Person p, ChatMessage m) {
        PrivateChat chat = getPrivateChat(p);
        chat.addPrivateMessage(m);
        notifyListeners(p, m);
    }

    public GroupChat getGroupChat(Group g) {
        if (groupMessages.containsKey(g.getUniqueId())) {
            return groupMessages.get(g.getUniqueId());
        }

        GroupChat chat = new GroupChat(g);
        groupMessages.put(g.getUniqueId(), chat);
        return chat;
    }

    public void addGroupMessage(Group g, ChatMessage m) {
        GroupChat chat = getGroupChat(g);
        chat.addMessage(m);
        notifyListeners(g, m);
    }

    private void notifyListeners(Contact c, ChatMessage m) {
        this.listeners.forEach(l -> l.onMessageReceived(c, m));
    }
}
