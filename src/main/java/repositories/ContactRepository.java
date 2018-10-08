package repositories;

import models.Group;
import models.Person;

import java.io.Serializable;
import java.util.*;

public class ContactRepository implements Serializable {
    private List<Person> myFriendRequests;
    private List<Person> incomingRequests;
    private Map<String, Person> friends;
    private Map<UUID, Group> groups;
    private Person self = null;

    private List<ContactListener> contactListeners;

    public ContactRepository() {
        this.myFriendRequests = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();
        this.friends = new HashMap<>();
        this.groups = new HashMap<>();

        this.contactListeners = new ArrayList<>();
    }

    public void registerListener(ContactListener l) {
        this.contactListeners.add(l);
    }

    public void addMyFriendRequest(Person person) {
        myFriendRequests.add(person);
    }

    public void removeMyFriendRequest(Person person) {
        myFriendRequests.remove(person);
        this.contactListeners.forEach(l -> l.onMyFriendRequestRemoved(person));
    }

    public void addIncomingFriendRequest(Person person) {
        this.incomingRequests.add(person);
        this.contactListeners.forEach((l) -> l.onIncomingFriendRequest(person));
    }

    public void removeIncomingFriendRequest(Person person) {
        this.incomingRequests.remove(person);
    }

    public void addFriend(Person friend) {
        this.friends.put(friend.getName(), friend);
        this.contactListeners.forEach(l -> l.onContactListUpdated());
    }

    public void removeFriend(Person friend) {
        this.friends.remove(friend.getName());
        this.contactListeners.forEach(l -> l.onContactListUpdated());
    }

    public void addGroup(Group group) {
        this.groups.put(group.getUniqueId(), group);
        this.contactListeners.forEach(l -> l.onContactListUpdated());
    }

    public void removeGroup(Group group) {
        this.groups.remove(group.getUniqueId());
    }

    public List<Person> getMyRequests() {
        return myFriendRequests;
    }

    public List<Person> getIncomingRequests() {
        return incomingRequests;
    }

    public void setSelf(Person self) {
        this.self = self;
    }

    public Person getSelf() {
        return self;
    }

    public Map<UUID, Group> getGroups() {
        return groups;
    }

    public Map<String, Person> getFriends() {
        return friends;
    }

}
