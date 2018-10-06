package services;

import dtos.GroupDTO;
import messages.Message;
import dtos.PersonDTO;
import models.Group;

import java.io.IOException;
import java.util.UUID;

public interface P2PService {
    PersonDTO start() throws IOException, InterruptedException;

    void shutdown();

    PersonDTO getPerson(String name) throws IOException, ClassNotFoundException;

    GroupDTO getGroup(UUID groupKey) throws IOException, ClassNotFoundException;

    void sendDirectMessage(PersonDTO receiver, Message message);

    void receiveMessage(MessageListener listener);

    void storeGroupInfoOnDHT(Group group) throws IOException;
}
