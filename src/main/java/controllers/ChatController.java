package controllers;

import dtos.FriendRequestMessage;
import dtos.UserDTO;
import models.Contact;
import models.ContactType;
import models.Group;
import models.Person;
import repositories.ChatRepository;
import repositories.ContactRepository;
import services.P2PService;
import util.ChatLogger;

import java.io.IOException;
import java.util.List;

public class ChatController implements MessageListener {

    private ChatRepository chatRepository;
    private P2PService service;

    public ChatController(P2PService service, ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.service = service;
    }

    public void listenForMessages() {
        service.receiveMessage(this);
    }

    public ContactRepository getContactRepository() {
        return chatRepository.getContactRepository();
    }

    private Person sendFriendRequest(String name) throws IOException, ClassNotFoundException {
        // TODO: We need to specify a sequence for adding friends

        // 1. Load data about friend from DHT
        UserDTO userDTO = service.getUser(name); // todo pass listeners for async
        ChatLogger.info("Client info retrieved: " + userDTO);

        // 2. Send FriendRequest
        service.sendDirectMessage(userDTO, new FriendRequestMessage(chatRepository.getClient().getUsername()));

        // 3. Create FriendRequest locally
        Person p = Person.create(userDTO);
        getContactRepository().addMyFriendRequest(p);
        return p;
    }

    public Person addFriend(String name) throws IOException, ClassNotFoundException {
        return sendFriendRequest(name);
    }

    public void addGroup(String name, List<Person> members) {
        Group group = new Group(name, members);
        chatRepository.addGroupToContactList(group);
        // TODO Inform friends about that they have been added to a group
    }

    public void removeContact(Contact contact) {
        if (contact.getType() == ContactType.PERSON) {
            chatRepository.removeFriendFromContactList((Person) contact);
        } else {
            chatRepository.removeGroupFromContactList((Group) contact);
        }
    }

    @Override
    public void onFriendRequest(Person p) {
        getContactRepository().addIncomingFriendRequest(p);
    }
}
