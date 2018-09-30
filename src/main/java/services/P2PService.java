package services;

import controllers.MessageListener;
import dtos.Message;
import dtos.UserDTO;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;

    void shutdown();

    UserDTO getUser(String name) throws IOException, ClassNotFoundException;

    void sendDirectMessage(UserDTO receiver, Message message);

    void receiveMessage(MessageListener listener);
}
