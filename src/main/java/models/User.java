package models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private int port;
    private String username;
    private UUID uniqueID;
    private String bootstrapIP;
    private int bootstrapPort;

    public User(int port) {
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

    public void setBootstrapPeer(String ip, int port) {
        bootstrapIP = ip;
        bootstrapPort = port;
    }

    public String getBootstrapIP() {
        return bootstrapIP;
    }

    public int getBootstrapPort() {
        return bootstrapPort;
    }

    public boolean hasBootstrapPeer() {
        return bootstrapIP != null && !"".equals(bootstrapIP);
    }

    public int getPort() {
        return port;
    }
}
