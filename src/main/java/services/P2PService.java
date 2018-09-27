package services;

import net.tomp2p.dht.FutureGet;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;

    FutureGet getUser(String name);

    // todo define parameters
    //  void get();
    //  void sendDirect();
}
