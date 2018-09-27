package controllers;

import models.Contact;
import models.ContactType;
import models.Group;
import models.Person;
import repositories.ChatRepository;
import services.P2PService;

import java.util.List;

public class ChatController {

    private ChatRepository chatRepository;
    private P2PService service;

    public ChatController(P2PService service, ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.service = service;

    }

    private void sendFriendRequest(String name) {
        // TODO: We need to specify a sequence for adding friends
        // 1. Load data about friend from DHT
//        User user = service.getUser(name);
        // 2. Create FriendRequest locally
//        chatRepository.addMyFriendRequest(user);
        // 3. Send FriendRequest
//        service.sendMessage();
    }

    public void addFriend(String name) {
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

}
