package controllers;

import dtos.PersonDTO;
import models.Group;
import models.NotaryMessage;
import models.Person;
import repositories.ContactRepository;
import repositories.MessageRepository;
import services.ChatService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService service) {
        this.chatService = service;
    }

    public ContactRepository getContactRepository() {
        return chatService.getContactRepository();
    }

    public MessageRepository getMessageRepository() {
        return chatService.getMessageRepository();
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

    public void sendPrivateMessage(Person recipient, String message) {
        chatService.sendChatMessage(recipient, message);
    }

    public CompletableFuture<Void> sendNotaryMessage(Person recipient, String message) throws NoSuchAlgorithmException {
        return chatService.sendNotaryChatMessage(recipient, message);
    }

    public CompletableFuture<Void> acceptNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException {
        return chatService.acceptNotaryMessage(recipient, m);
    }

    public CompletableFuture<Void> rejectNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException {
        return chatService.rejectNotaryMessage(recipient, m);
    }

    public void checkState(NotaryMessage m) throws Exception {
        chatService.checkState(m);
    }

    public void sendGroupMessage(Group group, String message) {
        chatService.sendChatMessage(group, message);
    }

    public void setSelf(PersonDTO self) {
        getContactRepository().setSelf(Person.create(self));

        // todo remove - add friends on start to avoid sending & accepting requests every time
//        getContactRepository().addFriend(Person.create(self));
//        getContactRepository().addFriend(new Person("Test 2", self.getPeerAddress()));
//        Group g = new Group("HSR");
//        g.join(getContactRepository().getSelf());
//        getContactRepository().addGroup(g);
    }

    public Person getSelf() {
        return getContactRepository().getSelf();
    }
}
