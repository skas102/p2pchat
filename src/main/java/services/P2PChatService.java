package services;

import dtos.*;
import models.Group;
import models.Person;
import repositories.ChatRepository;
import repositories.ContactRepository;
import util.ChatLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class P2PChatService implements ChatService {
    private ChatRepository chatRepository;
    private P2PService service;

    public P2PChatService(P2PService service, ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.service = service;
    }

    @Override
    public ContactRepository getContactRepository() {
        return chatRepository.getContactRepository();
    }

    @Override
    public Person sendFriendRequest(String name) throws IOException, ClassNotFoundException {
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

    @Override
    public void sendFriendConfirmation(Person person) {
        PersonDTO personDTO = person.createPersonDTO();

        // 1. Send FriendConfirmMessage
        service.sendDirectMessage(personDTO, new FriendConfirmMessage(chatRepository.getClient().getUsername()));
        ChatLogger.info("Confirmation message sent to: " + personDTO);

        // 2. Remove request from Incoming Friend Requests
        ContactRepository repo = getContactRepository();
        repo.removeIncomingFriendRequest(person);

        repo.addFriendToContactList(person);
    }

    @Override
    public void sendFriendRejection(Person person) {
        PersonDTO personDTO = person.createPersonDTO();
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 1. Send FriendRejectionMessage
        service.sendDirectMessage(personDTO, new FriendRejectionMessage(chatRepository.getClient().getUsername()));

        // 2. Remove request from Incoming Friend Requests
        ContactRepository repo = getContactRepository();
        repo.removeIncomingFriendRequest(person);
    }

    @Override
    public void sendFriendRemoval(Person person) {
        PersonDTO personDTO = person.createPersonDTO();
        ChatLogger.info("Person info retrieved: " + personDTO);

        // 1. Send FriendRemovalMessage
        service.sendDirectMessage(personDTO, new FriendRemovalMessage(chatRepository.getClient().getUsername()));

        // 2. Remove friend from contactlist
        ContactRepository repo = getContactRepository();
        repo.removeFriendFromContactList(person);
    }

    @Override
    public void sendGroupInvitation(String name, List<Person> members) throws IOException {
        Group group = new Group(name);
        group.join(getContactRepository().getSelf());
        getContactRepository().addGroupToContactList(group);
        for (Person member : members) {
            sendGroupInvitation(group, member);
        }
    }

    @Override
    public void sendGroupInvitation(Group group, Person person) throws IOException {
        group.join(person);
        service.storeGroupInfoOnDHT(group);
        PersonDTO personDTO = person.createPersonDTO();
        service.sendDirectMessage(personDTO, new GroupInvitationMessage(
                chatRepository.getClient().getUsername(),
                group.getUniqueID()
        ));
    }

    @Override
    public void sendGroupLeave(Group group) throws IOException {
        getContactRepository().removeGroupFromContactList(group);
        Person self = getContactRepository().getSelf();
        group.leave(self);
        service.storeGroupInfoOnDHT(group);

        for (Person member : group.getMembers()) {
            PersonDTO personDTO = member.createPersonDTO();
            service.sendDirectMessage(personDTO, new GroupLeaveMessage(
                    chatRepository.getClient().getUsername(),
                    group.getUniqueID()
            ));
        }
    }

    @Override
    public void sendGroupJoin(Group group, Person joiner) throws IOException {
        for (Person member : group.getMembers()) {
            PersonDTO personDTO = member.createPersonDTO();
            if (joiner.equals(member)) {
                sendGroupInvitation(group, joiner);
            } else {
                service.sendDirectMessage(personDTO, new GroupJoinMessage(
                        chatRepository.getClient().getUsername(),
                        group.getUniqueID(),
                        joiner.createPersonDTO()));
            }
        }

    }

    @Override
    public void onFriendRequest(Person p) {
        ChatLogger.info("Received Friend Request by person " + p.getName());
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
    public void onGroupInvitation(UUID groupKey) throws IOException, ClassNotFoundException {
        // TODO Optimization - Check if person exists locally
        GroupDTO groupDTO = service.getGroup(groupKey);
        List<Person> members = new ArrayList<>();
        for (String memberName : groupDTO.getMembers()) {
            PersonDTO personDTO = service.getPerson(memberName);
            members.add(Person.create(personDTO));
        }
        Group group = new Group(groupDTO.getGroupname(), groupKey, members);
        getContactRepository().addGroupToContactList(group);
    }

    @Override
    public void onGroupLeave(Person p, UUID groupKey) throws IOException, ClassNotFoundException {
        Group group = getContactRepository().getGroups().get(groupKey);
        group.leave(p);
    }

    @Override
    public void onGroupJoin(Person joiner, UUID groupKey) {
        Group group = getContactRepository().getGroups().get(groupKey);
        group.join(joiner);
    }

    @Override
    public void onFriendRemoval(Person p) {
        getContactRepository().removeFriendFromContactList(p);
    }

}
