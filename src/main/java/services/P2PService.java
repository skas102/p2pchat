package services;

import net.tomp2p.dht.FuturePut;

import java.io.IOException;

public interface P2PService {
    void start() throws IOException, InterruptedException;

    FuturePut put(String locationKey, Object value) throws IOException;

    FuturePut put(String locationKey, String domainKey, Object value) throws IOException;

    // todo define parameters
    //  void get();
    //  void sendDirect();
}
