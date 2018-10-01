package services;

import controllers.MessageListener;
import dtos.Message;
import dtos.PersonDTO;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;

    void shutdown();

    PersonDTO getPerson(String name) throws IOException, ClassNotFoundException;

    void sendDirectMessage(PersonDTO receiver, Message message);

    void receiveMessage(MessageListener listener);
}
