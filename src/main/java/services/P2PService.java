package services;

import dtos.UserDTO;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;
    void shutdown();

    UserDTO getUser(String name) throws IOException, ClassNotFoundException;

    void sendDirect(UserDTO receiver, String message);
    void receiveMessage();

    // todo define parameters
    //  void get();
    //  void sendDirect();
}
