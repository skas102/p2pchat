package services;

import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;

import java.io.IOException;
import java.net.InetAddress;

public class TomP2PService implements P2PService {
    private int port;
    private String bootstrapIP;
    private int bootstrapPort;

    private PeerDHT peerDHT;

    public TomP2PService(int port, String bootstrapIP, int bootstrapPort) {
        this.port = port;
        this.bootstrapIP = bootstrapIP;
        this.bootstrapPort = bootstrapPort;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        // todo replace hardcoded "Username" with the user chosen username
        Number160 uniqueNodeID = Number160.createHash("Username" + port);
        peerDHT = new PeerBuilderDHT(new PeerBuilder(uniqueNodeID).ports(4000).start()).start();
        System.out.println("Peer started at port " + port);

        if (bootstrapIP != null && !"".equals(bootstrapIP)) {
            System.out.println(String.format(
                    "Start bootstrapping using the peer at %s %d", bootstrapIP, bootstrapPort));
            peerDHT.peer()
                    .bootstrap()
                    .inetAddress(InetAddress.getByName(bootstrapIP))
                    .ports(bootstrapPort)
                    .start()
                    .awaitListeners();
        }
    }
}
