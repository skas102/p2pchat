package services;

import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;

import java.io.IOException;
import java.net.InetAddress;

public class TomP2PService implements P2PService {
    private int clientPort;
    private String bootstrapPeerIP;
    private int bootstrapPeerPort;

    private PeerDHT peerDHT;

    public TomP2PService(int clientPort, String bootstrapPeerIP, int bootstrapPeerPort) {
        this.clientPort = clientPort;
        this.bootstrapPeerIP = bootstrapPeerIP;
        this.bootstrapPeerPort = bootstrapPeerPort;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        // todo replace hardcoded "Username" with the user chosen username
        Number160 uniqueNodeID = Number160.createHash("Username" + clientPort);
        peerDHT = new PeerBuilderDHT(new PeerBuilder(uniqueNodeID).ports(4000).start()).start();
        System.out.println("Peer started at port " + clientPort);

        if (bootstrapPeerIP != null && !"".equals(bootstrapPeerIP)) {
            System.out.println(String.format(
                    "Start bootstrapping using the peer at %s %d", bootstrapPeerIP, bootstrapPeerPort));
            peerDHT.peer()
                    .bootstrap()
                    .inetAddress(InetAddress.getByName(bootstrapPeerIP))
                    .ports(bootstrapPeerPort)
                    .start()
                    .awaitListeners();
        }
    }
}
