package models;

import java.io.Serializable;
import java.util.UUID;

public class Client implements Serializable {
    private int port;
    private String username;
    private UUID uniqueID;

    public Client(int port) {
        this.uniqueID = UUID.randomUUID();
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username == null ? "User" + port : username;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public int getPort() {
        return port;
    }
}
