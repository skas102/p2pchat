import models.User;
import util.ChatLogger;

public class Main {

    // Usage: java Main <client-port> <bootstrap-peer-ip> <bootstrap-peer-port>
    public static void main(String[] args) {
        // todo replace all system log methods with the logger library
        // todo exception handling
        ChatLogger.logger.info("Started application");

        // By parameterizing the port, multiple chat applications could be started on the same machine
        int clientPort = args.length >= 1 ? Integer.parseInt(args[0]) : 4000;
        User user = new User(clientPort);

        if (args.length >= 2) {
            int bootstrapPort = args.length == 3 ? Integer.parseInt(args[2]) : 4000;
            user.setBootstrapPeer(args[1], bootstrapPort);
        }

        new ChatApplication(user).run();
    }
}
