package services;

import models.Group;
import models.NotaryMessage;
import models.Person;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import repositories.ContactRepository;
import repositories.MessageRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChatService extends MessageListener {
    ContactRepository getContactRepository();

    MessageRepository getMessageRepository();

    Person sendFriendRequest(String name) throws IOException, ClassNotFoundException;

    void sendFriendConfirmation(Person person);

    void sendFriendRejection(Person person);

    void sendFriendRemoval(Person person);

    void sendGroupInvitation(String name, List<Person> members) throws IOException;

    void sendGroupInvitation(Group group, Person person) throws IOException;

    void sendGroupLeave(Group group) throws IOException;

    void sendGroupJoin(Group group, Person joiner) throws IOException;

    void sendChatMessage(Person recipient, String message);

    void sendChatMessage(Group recipient, String message);

    CompletableFuture<Void> sendNotaryChatMessage(Person recipient, String message) throws NoSuchAlgorithmException;

    CompletableFuture<Void> acceptNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException;

    CompletableFuture<Void> rejectNotaryMessage(Person recipient, NotaryMessage m) throws NoSuchAlgorithmException;

    void checkState(NotaryMessage m) throws Exception;
}
