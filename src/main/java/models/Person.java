package models;

import dtos.PersonDTO;
import net.tomp2p.peers.PeerAddress;

import java.util.UUID;

public class Person implements Contact {

    private String username;
    private PeerAddress peerAddress;
    private boolean isOnline;

    public Person(String username, PeerAddress peerAddress) { this(username, peerAddress, false); }

    public Person(String username, PeerAddress peerAddress, boolean isOnline) {
        this.username = username;
        this.peerAddress = peerAddress;
        this.isOnline = isOnline;
    }

    public static Person create(PersonDTO personDTO) {
        return new Person(personDTO.getUsername(), personDTO.getPeerAddress());
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

    public PersonDTO createPersonDTO() {
        return new PersonDTO(username, peerAddress);
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return username.equals(other.username);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + username.hashCode();
        return result;
    }
}
