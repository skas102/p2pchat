package repositories;

import models.Group;
import models.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository implements Serializable {
    private List<Person> myFriendRequests;
    private List<Person> incomingRequests;
    private List<Person> friends;
    private List<Group> groups;
    private Person self = null;

    private List<ContactListener> contactListeners;

    public ContactRepository() {
        this.myFriendRequests = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.groups = new ArrayList<>();

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

    public void addFriendToContactList(Person friend) {
        this.friends.add(friend);
        this.contactListeners.forEach(l -> l.onContactListUpdated());
    }

    public void removeFriendFromContactList(Person friend) {
        this.friends.remove(friend);
        this.contactListeners.forEach(l -> l.onContactListUpdated());
    }

    public void addGroupToContactList(Group group) {
        this.groups.add(group);
    }

    public void removeGroupFromContactList(Group group) {
        this.groups.remove(group);
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

    public List<Group> getGroups() {
        return groups;
    }

    public List<Person> getFriends() {
        return friends;
    }


}
