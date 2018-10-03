package services;

import dtos.GroupDTO;
import dtos.Message;
import dtos.PersonDTO;
import models.Group;

import java.io.IOException;

public interface P2PService {
    PersonDTO start() throws IOException, InterruptedException;

    void shutdown();

    PersonDTO getPerson(String name) throws IOException, ClassNotFoundException;

    GroupDTO getGroup(String groupKey) throws IOException, ClassNotFoundException;

    void sendDirectMessage(PersonDTO receiver, Message message);

    void receiveMessage(MessageListener listener);

    void storeGroupInfoOnDHT(Group group) throws IOException;
}
