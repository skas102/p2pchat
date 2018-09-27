package services;

import models.User;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.dht.PutBuilder;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;
import util.StringUtil;

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

    @Override
    public FuturePut put(String locationKey, Object value) throws IOException {
        return put(locationKey, null, value);
    }

    @Override
    public FuturePut put(String locationKey, String domainKey, Object value) throws IOException {
        PutBuilder builder = peerDHT.put(Number160.createHash(locationKey))
                .data(new Data(value));

        if (!StringUtil.isNullOrEmpty(domainKey)) {
            builder = builder.domainKey(Number160.createHash(domainKey));
        }
        return builder.start();
    }
}
