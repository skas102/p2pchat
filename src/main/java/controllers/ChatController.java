package controllers;

import dtos.PersonDTO;
import models.Group;
import models.Person;
import repositories.ContactRepository;
import services.ChatService;

import java.io.IOException;
import java.util.List;

public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService service) {
        this.chatService = service;
    }

    public ContactRepository getContactRepository() {
        return chatService.getContactRepository();
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
        chatService.sendGroupInvitation(name, members);
    }

    public void leaveGroup(Group group) throws IOException {
        chatService.sendGroupLeave(group);
    }

    public void joinGroup(Group group, Person joiner) throws IOException {
        chatService.sendGroupJoin(group, joiner);
    }

    public void setSelf(PersonDTO self) {
        getContactRepository().setSelf(Person.create(self));
    }
}
