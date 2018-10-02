package controllers;

import dtos.*;
import models.*;
import repositories.ChatRepository;
import repositories.ContactRepository;
import services.P2PService;
import util.ChatLogger;

import java.io.IOException;
import java.util.ArrayList;
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
        PersonDTO personDTO = person.createPersonDTO();

        // 1. Send FriendConfirmMessage
        service.sendDirectMessage(personDTO, new FriendConfirmMessage(chatRepository.getClient().getUsername()));
        ChatLogger.info("Confirmation message sent to: " + personDTO);

        // 2. Remove request from Incoming Friend Requests
        ContactRepository repo = getContactRepository();
        repo.removeIncomingFriendRequest(person);
    }

    private void sendFriendRejection(Person person) {
        PersonDTO personDTO = person.createPersonDTO();
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 1. Send FriendRejectionMessage
        service.sendDirectMessage(personDTO, new FriendRejectionMessage(chatRepository.getClient().getUsername()));

        // 2. Remove request from Incoming Friend Requests
        ContactRepository repo = getContactRepository();
        repo.removeIncomingFriendRequest(person);
    }

    private void sendFriendRemoval(Person person) {
        PersonDTO personDTO = person.createPersonDTO();
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 1. Send FriendRemovalMessage
        service.sendDirectMessage(personDTO, new FriendRemovalMessage(chatRepository.getClient().getUsername()));

        // 2. Remove friend from contactlist
        ContactRepository repo = getContactRepository();
        repo.removeFriendFromContactList(person);
    }

    private void sendGroupInvitation(Group group) {
        // TODO how do we check that everyone invited to the group really gets added to the group? Do we need to check
        // if they are online and how do we handle message loss?
        for (Person member : group.getMembers()) {
            PersonDTO personDTO = member.createPersonDTO();
            service.sendDirectMessage(personDTO, new GroupInvitationMessage(
                    chatRepository.getClient().getUsername(),
                    group.getUniqueID().toString())
            );
        }
    }

    private void sendGroupLeave(Group group) {
        for (Person member : group.getMembers()) {
            PersonDTO personDTO = member.createPersonDTO();
            service.sendDirectMessage(personDTO, new GroupLeaveMessage(
                    chatRepository.getClient().getUsername(),
                    group.getUniqueID().toString())
            );
        }
    }


    public Person addFriend(String name) throws IOException, ClassNotFoundException {
        return sendFriendRequest(name);
    }

    public void confirmFriend(Person friend) {
        sendFriendConfirmation(friend);
    }

    public void rejectFriend(Person friend) { sendFriendRejection(friend); }

    public void removeFriend(Person friend) {
        sendFriendRemoval(friend);
    }

    public void createGroup(String name, List<Person> members) throws IOException {
        Group group = new Group(name, members);
        service.storeGroupInfoOnDHT(group);
        sendGroupInvitation(group);
        getContactRepository().addGroupToContactList(group);
    }

    public void leaveGroup(Group group) throws IOException {
        getContactRepository().removeGroupFromContactList(group);
        Person self = chatRepository.getContactRepository().getSelf();
        group.leave(self);
        service.storeGroupInfoOnDHT(group);
        sendGroupLeave(group);
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

    @Override
    public void onFriendRejection(Person p) {
        ContactRepository repo = getContactRepository();
        repo.removeMyFriendRequest(p);
    }

    @Override
    public void onGroupInvitation(String groupKey) throws IOException, ClassNotFoundException {
        // TODO Optimization - Check if person exists locally
        GroupDTO groupDTO = service.getGroup(groupKey);
        List<Person> members = new ArrayList<>();
        for (String memberName : groupDTO.getMembers()) {
            PersonDTO personDTO = service.getPerson(memberName);
            members.add(Person.create(personDTO));
        }
        Group group = new Group(groupDTO.getGroupname(), members);
        getContactRepository().addGroupToContactList(group);
    }

    @Override
    public void onGroupLeave(Person p, String groupKey) throws IOException, ClassNotFoundException {
        List<Group> groups = getContactRepository().getContactList().getGroups();
        Group group = null;
        for (Group g : groups) {
            if (g.getUniqueID().toString().equals(groupKey)) {
                group = g;
            }
        }
        group.leave(p);
    }

    public void setSelf(PersonDTO self) {
        chatRepository.getContactRepository().setSelf(Person.create(self));
    }

    @Override
    public void onFriendRemoval(Person p) {
        getContactRepository().removeFriendFromContactList(p);
    }

}
