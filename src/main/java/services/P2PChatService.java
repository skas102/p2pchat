package services;

import dtos.GroupDTO;
import dtos.PersonDTO;
import messages.*;
import models.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import repositories.ChatRepository;
import repositories.ContactRepository;
import repositories.MessageRepository;
import util.ChatLogger;
import util.StringUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class P2PChatService implements ChatService {
    private ChatRepository chatRepository;
    private P2PService service;
    private NotaryService notaryService;

    public P2PChatService(P2PService service, ChatRepository chatRepository, NotaryService notaryService) {
        this.chatRepository = chatRepository;
        this.service = service;
        this.notaryService = notaryService;
    }

    @Override
    public ContactRepository getContactRepository() {
        return chatRepository.getContactRepository();
    }

    @Override
    public MessageRepository getMessageRepository() {
        return chatRepository.getMessageRepository();
    }

    @Override
    public Person sendFriendRequest(String name) throws IOException, ClassNotFoundException {
        // 1. Load data about friend from DHT
        PersonDTO personDTO = service.getPerson(name);
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

        repo.addFriend(person);
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
        repo.removeFriend(person);
    }

    @Override
    public void sendGroupInvitation(String name, List<Person> members) throws IOException {
        Group group = new Group(name);
        group.join(getContactRepository().getSelf());
        getContactRepository().addGroup(group);
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
                group.getUniqueId()
        ));
    }

    @Override
    public void sendGroupLeave(Group group) throws IOException {
        getContactRepository().removeGroup(group);
        Person self = getContactRepository().getSelf();
        group.leave(self);
        service.storeGroupInfoOnDHT(group);

        for (Person member : group.getMembers()) {
            PersonDTO personDTO = member.createPersonDTO();
            service.sendDirectMessage(personDTO, new GroupLeaveMessage(
                    chatRepository.getClient().getUsername(),
                    group.getUniqueId()
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
                        group.getUniqueId(),
                        joiner.createPersonDTO()));
            }
        }
    }

    @Override
    public void sendChatMessage(Person recipient, String message) {
        ChatLogger.info("Send private message to " + recipient.getName());
        ChatMessage chatMessage = new ChatMessage(chatRepository.getClient().getUsername(), message);
        getMessageRepository().addFriendMessage(recipient, chatMessage);

        service.sendDirectMessage(recipient.createPersonDTO(), new NewChatMessage(
                recipient.getType(),
                recipient.getName(),
                chatMessage.createDTO()
        ));
    }

    @Override
    public void sendChatMessage(Group recipient, String message) {
        ChatLogger.info("Send group message to " + recipient.getName());
        ChatMessage chatMessage = new ChatMessage(chatRepository.getClient().getUsername(), message);
        getMessageRepository().addGroupMessage(recipient, chatMessage);

        Person self = getContactRepository().getSelf();
        recipient.getMembers().forEach(r -> {
            if (!self.equals(r)) {
                service.sendDirectMessage(r.createPersonDTO(), new NewChatMessage(
                        recipient.getType(),
                        recipient.getUniqueId().toString(),
                        chatMessage.createDTO()
                ));
            }
        });
    }

    @Override
    public CompletableFuture<Void> sendNotaryChatMessage(Person recipient, String message)
            throws NoSuchAlgorithmException {
        ChatLogger.info("Sending notary message to " + recipient.getName());
        NotaryMessage notaryMessage = new NotaryMessage(chatRepository.getClient().getUsername(), message);
        notaryMessage.setState(NotaryState.SENDING);
        byte[] hash = notaryMessage.getHash();
        getMessageRepository().addNotaryMessage(recipient, notaryMessage);

        return CompletableFuture.supplyAsync(() -> {
            try {
                // add the message to the blockchain first
                TransactionReceipt tx = notaryService.addMessageHash(hash).join();
                ChatLogger.info(String.format("addMessageHash Transaction completed, hash=%s",
                        tx.getTransactionHash()));

                // send p2p message only when transaction is successful
                service.sendDirectMessage(recipient.createPersonDTO(), new NewNotaryChatMessage(
                        recipient.getName(),
                        notaryMessage.createDTO()));
                notaryMessage.setState(NotaryState.PENDING);
                return null;
            } catch (Exception ex) {
                ChatLogger.error(String.format("addMessageHash Transaction failed: %s", ex.getMessage()));
                notaryMessage.setState(NotaryState.FAILED);
                throw ex;
            }
        });
    }

    @Override
    public CompletableFuture<Void> acceptNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException {
        ChatLogger.info("Sending accept notary message to " + recipient.getName());
        byte[] hash = m.getHash();

        return CompletableFuture.supplyAsync(() -> {
            try {
                TransactionReceipt tx = notaryService.acceptMessage(hash).join();
                ChatLogger.info(String.format("acceptMessage Transaction completed, hash=%s",
                        tx.getTransactionHash()));

                m.setState(NotaryState.ACCEPTED);
                return null;
            } catch (Exception ex) {
                ChatLogger.error(String.format("acceptMessage Transaction failed: %s", ex.getMessage()));
                throw ex;
            }
        });
    }

    @Override
    public CompletableFuture<Void> rejectNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException {
        ChatLogger.info("Sending accept notary message to " + recipient.getName());
        byte[] hash = m.getHash();

        return CompletableFuture.supplyAsync(() -> {
            try {
                TransactionReceipt tx = notaryService.rejectMessage(hash).join();
                ChatLogger.info(String.format("rejectMessage Transaction completed, hash=%s",
                        tx.getTransactionHash()));

                m.setState(NotaryState.REJECTED);
                return null;
            } catch (Exception ex) {
                ChatLogger.error(String.format("rejectMessage Transaction failed: %s", ex.getMessage()));
                throw ex;
            }
        });
    }

    @Override
    public void checkState(NotaryMessage m) throws Exception {
        byte[] hash = m.getHash();
        ChatLogger.info("Checking message state of " + hash);

        int value = notaryService.getMessageState(hash).intValue();
        NotaryState state = NotaryState.values()[value];
        ChatLogger.info(String.format("State of %s: %d <==> %s", StringUtil.bytesToHex(hash), value, state));

        m.setState(state);
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
        repo.addFriend(p);
    }

    @Override
    public void onFriendRejection(Person p) {
        ContactRepository repo = getContactRepository();
        repo.removeMyFriendRequest(p);
    }

    @Override
    public void onGroupInvitation(UUID groupKey) {
        new Thread(() -> {
            try {
                GroupDTO groupDTO = service.getGroup(groupKey);
                List<Person> members = new ArrayList<>();
                for (String memberName : groupDTO.getMembers()) {
                    PersonDTO personDTO = service.getPerson(memberName);
                    members.add(Person.create(personDTO));
                }
                Group group = new Group(groupDTO.getGroupname(), groupDTO.getGroupKey(), members);
                getContactRepository().addGroup(group);
            } catch (IOException | ClassCastException | ClassNotFoundException e) {
                ChatLogger.error("Processing Group Invitation failed " + e.getMessage());
            }
        }).start();
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
    public void onChatMessageReceived(NewChatMessage newMessage) {
        ContactType type = newMessage.getContactType();
        ChatMessage chatMessage = ChatMessage.create(newMessage.getMessageDTO());
        if (type == ContactType.GROUP) {
            UUID groupKey = UUID.fromString(newMessage.getRecipientIdentifier());
            Group group = getContactRepository().getGroups().get(groupKey);
            if (group != null) {
                getMessageRepository().addGroupMessage(group, chatMessage);
            }
        } else {
            Person friend = getContactRepository().getFriends().get(newMessage.getSenderUsername());
            if (friend != null) {
                getMessageRepository().addFriendMessage(friend, chatMessage);
            }
        }
    }

    @Override
    public void onNotaryChatMessageReceived(NewNotaryChatMessage newNotaryChatMessage) {
        NotaryMessage notaryMessage = NotaryMessage.create(newNotaryChatMessage.getMessageDTO());
        Person friend = getContactRepository().getFriends().get(newNotaryChatMessage.getSenderUsername());
        if (friend != null) {
            getMessageRepository().addNotaryMessage(friend, notaryMessage);
        }
    }

    @Override
    public void onFriendRemoval(Person p) {
        getContactRepository().removeFriend(p);
    }

}
