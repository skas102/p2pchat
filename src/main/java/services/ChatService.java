package services;

import models.Group;
import models.Person;
import repositories.ContactRepository;
import repositories.MessageRepository;

import java.io.IOException;
import java.util.List;

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
}
