package dtos;

import net.tomp2p.peers.PeerAddress;

import java.io.Serializable;

public class PersonDTO implements Serializable {
    private String username;
    private PeerAddress peerAddress;

    public PersonDTO(String username, PeerAddress peerAddress) {
        this.username = username;
        this.peerAddress = peerAddress;
    }

    public String getUsername() {
        return username;
    }

    public PeerAddress getPeerAddress() {
        return peerAddress;
    }

    @Override
    public String toString() {
        return String.format("%s @%s", username, peerAddress);
    }
}
