public class Main {

    // Usage: java Main <your-port> <bootstrap-peer-ip> <bootstrap-peer-port>
    public static void main(String[] args) {
        // todo replace all system log methods with the logger library
        // todo exception handling

        // By parameterizing the port, multiple chat applications could be started on the same machine
        int port = args.length >= 1 ? Integer.parseInt(args[0]) : 4000;
        String bootstrapPeerIP = args.length >= 2 ? args[1] : "";
        int bootstrapPeerPort = args.length == 3 ? Integer.parseInt(args[2]) : 4000;

        new ChatApplication(port, bootstrapPeerIP, bootstrapPeerPort).run();
    }
}
