package controllers;

import dtos.PersonDTO;
import models.Group;
import models.Person;
import repositories.ChatRepository;
import repositories.ContactRepository;
import services.ChatService;
import services.P2PChatService;
import services.P2PService;

import java.io.IOException;
import java.util.List;

public class ChatController {

    private ChatService chatService;
    private ChatRepository chatRepository;

    public ChatController(P2PService service, ChatRepository chatRepository) {
        this.chatService = new P2PChatService(service, chatRepository);
        this.chatRepository = chatRepository;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public ContactRepository getContactRepository() {
        return chatRepository.getContactRepository();
    }

    public Person addFriend(String name) throws IOException, ClassNotFoundException {
        return chatService.sendFriendRequest(name);
    }

    public void confirmFriend(Person friend) {
        chatService.sendFriendConfirmation(friend);
    }

    public void rejectFriend(Person friend) {
        chatService.sendFriendRejection(friend);
    }

    public void removeFriend(Person friend) {
        chatService.sendFriendRemoval(friend);
    }

    public void createGroup(String name, List<Person> members) throws IOException {
        Group group = new Group(name, members);
        chatService.sendGroupInvitation(group);
    }

    public void leaveGroup(Group group) throws IOException {
        chatService.sendGroupLeave(group);
    }

    public void setSelf(PersonDTO self) {
        getContactRepository().setSelf(Person.create(self));
    }
}
