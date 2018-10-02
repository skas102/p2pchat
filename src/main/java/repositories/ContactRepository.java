package repositories;

import models.ContactList;
import models.Group;
import models.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository implements Serializable {
    private List<Person> myFriendRequests;
    private List<Person> incomingRequests;
    private ContactList contactList;
    private Person self = null;

    private List<ContactListener> contactListeners;

    public ContactRepository() {
        this.myFriendRequests = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();

        this.contactListeners = new ArrayList<>();

        this.contactList = new ContactList(new ArrayList<>(), new ArrayList<>());
    }

    public void registerListener(ContactListener l) {
        this.contactListeners.add(l);
    }

    public void addMyFriendRequest(Person person) {
        myFriendRequests.add(person);
    }

    public void removeMyFriendRequest(Person person) {myFriendRequests.remove(person);}

    public void addIncomingFriendRequest(Person person) {
        this.incomingRequests.add(person);
        this.contactListeners.forEach((l) -> l.onIncomingFriendRequest(person));
    }

    public void removeIncomingFriendRequest(Person person) {
        this.incomingRequests.remove(person);
    }

    public void addFriendToContactList(Person friend) {
        this.contactList.getFriends().add(friend);
    }

    public void removeFriendFromContactList(Person friend) {
        this.contactList.getFriends().remove(friend);
    }

    public void addGroupToContactList(Group group) {
        this.contactList.getGroups().add(group);
    }

    public void removeGroupFromContactList(Group group) {
        this.contactList.getGroups().remove(group);
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

    public Person getSelf() { return self;}

    public ContactList getContactList() {
        return this.contactList;
    }
}
