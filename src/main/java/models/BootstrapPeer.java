package models;

public class BootstrapPeer {
    private String ip;
    private int port;

    public BootstrapPeer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
