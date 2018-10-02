package models;

import java.util.List;

public class ContactList {

    // TODO Use Hashmap
    // TODO Move up to ContactRepository
    private List<Person> friends;
    private List<Group> groups;

    public ContactList(List<Person> friends, List<Group> groups) {
        this.friends = friends;
        this.groups = groups;
    }

    public List<Person> getFriends() {
        return this.friends;
    }

    public List<Group> getGroups() {
        return this.groups;
    }
}
