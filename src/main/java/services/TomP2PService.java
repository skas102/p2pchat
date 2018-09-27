package services;

import models.User;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;

import java.io.IOException;
import java.net.InetAddress;

public class TomP2PService implements P2PService {
    private User user;
    private PeerDHT peerDHT;

    public TomP2PService(User user) {
        this.user = user;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        Number160 uniqueNodeID = Number160.createHash(user.getUniqueID().toString());
        peerDHT = new PeerBuilderDHT(new PeerBuilder(uniqueNodeID).ports(user.getPort()).start()).start();
        System.out.println("Peer started at port " + user.getPort());

        if (user.hasBootstrapPeer()) {
            System.out.println(String.format(
                    "Start bootstrapping using the peer at %s %d", user.getBootstrapIP(), user.getBootstrapPort()));
            peerDHT.peer()
                    .bootstrap()
                    .inetAddress(InetAddress.getByName(user.getBootstrapIP()))
                    .ports(user.getBootstrapPort())
                    .start()
                    .awaitListeners();
        }
    }
}
