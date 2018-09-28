package services;

import dtos.UserDTO;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;

    UserDTO getUser(String name) throws IOException, ClassNotFoundException;

    // todo define parameters
    //  void get();
    //  void sendDirect();
}
