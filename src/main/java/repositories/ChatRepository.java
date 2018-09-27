package repositories;

import models.ContactList;
import models.Group;
import models.Person;
import models.User;

import java.io.Serializable;
import java.util.ArrayList;

// todo Functionalities are to be defined.
public class ChatRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private ContactList contactList;

    public ChatRepository(User user) {
        this.user = user;
    }

    private void init() {
        contactList = new ContactList(new ArrayList<>(), new ArrayList<>());
    }

    public String getProfileName() {
        return this.user.getUsername();
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
