package repositories;

import models.Client;

import java.io.Serializable;

public class ChatRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private Client client;
    private ContactRepository contactRepository;
    private MessageRepository messageRepository;

    public ChatRepository(Client client) {
        this.client = client;
        this.contactRepository = new ContactRepository();
        this.messageRepository = new MessageRepository();
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

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }
}
