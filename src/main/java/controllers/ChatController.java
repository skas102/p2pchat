package controllers;

import dtos.UserDTO;
import models.Contact;
import models.ContactType;
import models.Group;
import models.Person;
import net.tomp2p.dht.FutureGet;
import repositories.ChatRepository;
import services.P2PService;

import java.io.IOException;
import java.util.List;

public class ChatController {

    private ChatRepository chatRepository;
    private P2PService service;

    public ChatController(P2PService service, ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.service = service;

    }

    private void sendFriendRequest(String name) throws IOException, ClassNotFoundException {
        // TODO: We need to specify a sequence for adding friends

        // 1. Load data about friend from DHT
        // TODO: await is a blocking operation. It should be refactored with listener/observer to process asynchronously.
        FutureGet futureUser = service.getUser(name).awaitUninterruptibly();
        UserDTO userDTO = (UserDTO) futureUser.data().object();

        // 2. Create FriendRequest locally
        // chatRepository.addMyFriendRequest(user);
        // 3. Send FriendRequest
        // service.sendMessage();
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

}
