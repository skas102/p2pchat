package services;

import controllers.MessageListener;
import models.Group;
import models.Person;

import java.io.IOException;

public interface ChatService extends MessageListener {
    Person sendFriendRequest(String name) throws IOException, ClassNotFoundException;

    void sendFriendConfirmation(Person person);

    void sendFriendRejection(Person person);

    void sendFriendRemoval(Person person);

    void sendGroupInvitation(Group group) throws IOException;

    void sendGroupLeave(Group group) throws IOException;
}
