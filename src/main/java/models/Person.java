package models;

import dtos.UserDTO;
import net.tomp2p.peers.PeerAddress;

public class Person implements Contact {

    private String username;
    private PeerAddress peerAddress;
    private boolean isOnline;

    public Person(String username, PeerAddress peerAddress) {
        this(username, peerAddress, false);
    }

    public Person(String username, PeerAddress peerAddress, boolean isOnline) {
        this.username = username;
        this.peerAddress = peerAddress;
        this.isOnline = isOnline;
    }

    public static Person create(UserDTO userDTO) {
        return new Person(userDTO.getUsername(), userDTO.getPeerAddress());
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public boolean getStatus() {
        return isOnline;
    }

    @Override
    public ContactType getType() {
        return ContactType.PERSON;
    }

    public PeerAddress getPeerAddress() {
        return this.peerAddress;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public UserDTO createUserDTO() {
        return new UserDTO(username, peerAddress);
    }

    @Override
    public String toString() {
        return username;
    }
}
