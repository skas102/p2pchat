import controllers.ChatController;
import services.P2PService;
import services.TomP2PService;
import views.MainWindow;

import java.io.IOException;

public class ChatApplication {
    private P2PService p2pService;
    private ChatController chatController;

    public ChatApplication(int clientPort, String bootstrapPeerIP, int bootstrapPeerPort) {
        p2pService = new TomP2PService(clientPort, bootstrapPeerIP, bootstrapPeerPort);

        // todo create ChatRepository from file
        chatController = new ChatController(p2pService);
    }

    public void run() {
        try {
            p2pService.start();
        } catch (InterruptedException | IOException ex) {
            System.err.println("Starting P2P Service failed - " + ex.getLocalizedMessage());
            System.exit(1);
        }

        // todo pass controller to the view
        new MainWindow();
    }
}
