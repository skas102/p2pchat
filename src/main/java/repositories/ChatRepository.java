package repositories;

import models.Client;
import models.ContactList;
import models.Group;
import models.Person;

import java.io.Serializable;
import java.util.ArrayList;

// todo Functionalities are to be defined.
public class ChatRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private Client client;
    private ContactList contactList; // todo move to ContactRepository
    private ContactRepository contactRepository;

    public ChatRepository(Client client) {
        this.client = client;
        this.contactRepository = new ContactRepository();
    }

    private void init() {
        contactList = new ContactList(new ArrayList<>(), new ArrayList<>());
    }

    public Client getClient() {
        return client;
    }

    public String getProfileName() {
        return this.client.getUsername();
    }

    public ContactRepository getContactRepository() {
        return contactRepository;
    }

    public void addFriendToContactList(Person friend) {
        contactList.getFriends().add(friend);
    }

    public void removeFriendFromContactList(Person friend) {
        contactList.getFriends().remove(friend);
    }

    public void addGroupToContactList(Group group) {
        contactList.getGroups().add(group);
    }

    public void removeGroupFromContactList(Group group) {
        contactList.getGroups().remove(group);
    }


}
