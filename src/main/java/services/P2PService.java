package services;

import dtos.UserDTO;

import java.io.IOException;
import java.net.UnknownHostException;

public interface P2PService {
    void start() throws IOException, InterruptedException;
    void shutdown();

    UserDTO getUser(String name) throws IOException, ClassNotFoundException;

    void sendDirect(UserDTO receiver, String message) throws UnknownHostException;
    void receiveMessage();

    // todo define parameters
    //  void get();
    //  void sendDirect();
}
