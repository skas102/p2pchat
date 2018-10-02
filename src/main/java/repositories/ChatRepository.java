package repositories;

import models.Client;

import java.io.Serializable;

// todo Functionalities are to be defined.
public class ChatRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private Client client;
    private ContactRepository contactRepository;

    public ChatRepository(Client client) {
        this.client = client;
        this.contactRepository = new ContactRepository();
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


}
