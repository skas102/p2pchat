package controllers;

import dtos.FriendRequestMessage;
import dtos.UserDTO;
import models.Contact;
import models.ContactType;
import models.Group;
import models.Person;
import repositories.ChatRepository;
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

    private void sendFriendRequest(String name) throws IOException, ClassNotFoundException {
        // TODO: We need to specify a sequence for adding friends

        // 1. Load data about friend from DHT
        UserDTO userDTO = service.getUser(name); // todo pass listeners for async
        ChatLogger.info("Client info retrieved: " + userDTO);

        // 2. Send FriendRequest
        service.sendDirectMessage(userDTO, new FriendRequestMessage(chatRepository.getClient().getUsername()));

        // 3. Create FriendRequest locally
        // chatRepository.addMyFriendRequest(user);
    }

    public void addFriend(String name) throws IOException, ClassNotFoundException {
        sendFriendRequest(name);
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
    public void onFriendRequest(FriendRequestMessage m) {

    }
}
