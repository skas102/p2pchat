package repositories;

import models.ContactList;

import java.util.ArrayList;

// todo Functionalities are to be defined.
public class ChatRepository {
    private ContactList contactList;

    public ChatRepository() {
    }

    private void load() {
        // todo load from file (different file name depends on username to support multiple application)
        // if no file is found, init all fields
        init();
    }

    private void init() {
        contactList = new ContactList(new ArrayList<>(), new ArrayList<>());
    }
}
