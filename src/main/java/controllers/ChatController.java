package controllers;

import dtos.FriendConfirmMessage;
import dtos.FriendRequestMessage;
import dtos.PersonDTO;
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

        // 1. Load data about friend from DHT
        PersonDTO personDTO = service.getPerson(name); // todo pass listeners for async
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 2. Send FriendRequest
        service.sendDirectMessage(personDTO, new FriendRequestMessage(chatRepository.getClient().getUsername()));

        // 3. Create FriendRequest locally
        Person p = Person.create(personDTO);
        getContactRepository().addMyFriendRequest(p);
        return p;
    }

    private void sendFriendConfirmation(Person person) {
        PersonDTO personDTO = new PersonDTO(person.getName(),person.getPeerAddress());
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 1. Send FriendConfirmMessage
        service.sendDirectMessage(personDTO, new FriendConfirmMessage(chatRepository.getClient().getUsername()));

        // 2. Remove request from Incoming Friend Requests
        ContactRepository repo = getContactRepository();
        repo.removeIncomingFriendRequest(person);
    }

    public Person addFriend(String name) throws IOException, ClassNotFoundException {
        return sendFriendRequest(name);
    }

    public void confirmFriend(Person friend) {
        sendFriendConfirmation(friend);
    }

    @Override
    public void onFriendRequest(Person p) {
        getContactRepository().addIncomingFriendRequest(p);
    }

    @Override
    public void onFriendConfirm(Person p) {
        ContactRepository repo = getContactRepository();
        repo.removeMyFriendRequest(p);
        repo.addFriendToContactList(p);
    }
}
