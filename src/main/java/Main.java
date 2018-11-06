import models.BootstrapPeer;
import models.Client;
import util.ChatLogger;

public class Main {

    // Usage: java Main <client-port> <bootstrap-peer-ip> <bootstrap-peer-port>
    public static void main(String[] args) {
        ChatLogger.info("Started application");

        // By parameterizing the port, multiple chat applications could be started on the same machine
        int clientPort = args.length >= 1 ? Integer.parseInt(args[0]) : 4000;
        Client client = new Client(clientPort);

        BootstrapPeer bootstrapPeer = null;
        if (args.length >= 2) {
            bootstrapPeer = new BootstrapPeer(
                    args[1], args.length == 3 ? Integer.parseInt(args[2]) : 4000);
        }

        new ChatApplication(client, bootstrapPeer).run();
    }
}
