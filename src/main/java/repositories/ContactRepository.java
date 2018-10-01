package repositories;

import models.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository implements Serializable {
    private List<Person> myFriendRequests;
    private List<Person> incomingRequests;

    private List<ContactListener> contactListeners;

    public ContactRepository() {
        this.myFriendRequests = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();

        this.contactListeners = new ArrayList<>();
    }

    public void registerListener(ContactListener l) {
        this.contactListeners.add(l);
    }

    public void addMyFriendRequest(Person person) {
        myFriendRequests.add(person);
    }

    public void addIncomingFriendRequest(Person person) {
        this.incomingRequests.add(person);
        this.contactListeners.forEach((l) -> l.onIncomingFriendRequest(person));
    }

    public List<Person> getMyRequests() {
        return myFriendRequests;
    }

    public List<Person> getIncomingRequests() {
        return incomingRequests;
    }
}
