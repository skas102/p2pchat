package repositories;

import models.ContactList;
import models.Group;
import models.Person;
import models.Client;

import java.io.Serializable;
import java.util.ArrayList;

// todo Functionalities are to be defined.
public class ChatRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private Client client;
    private ContactList contactList;

    public ChatRepository(Client client) {
        this.client = client;
    }

    private void init() {
        contactList = new ContactList(new ArrayList<>(), new ArrayList<>());
    }

    public Client getClient(){
        return client;
    }

    public String getProfileName() {
        return this.client.getUsername();
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
