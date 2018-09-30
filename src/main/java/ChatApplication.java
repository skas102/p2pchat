import controllers.ChatController;
import models.BootstrapPeer;
import models.Client;
import repositories.ChatRepository;
import services.P2PService;
import services.TomP2PService;
import views.MainWindow;

import java.io.IOException;

public class ChatApplication {
    private P2PService p2pService;
    private ChatController chatController;

    public ChatApplication(Client client, BootstrapPeer bootstrapPeer) {
        p2pService = new TomP2PService(client, bootstrapPeer);

        // todo ask client for the username if first time, otherwise load from data file
        ChatRepository repo = new ChatRepository(client);
        chatController = new ChatController(p2pService, repo);
    }

    public void run() {
        try {
            p2pService.start();
        } catch (InterruptedException | IOException ex) {
            System.err.println("Starting P2P Service failed - " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }

        // todo pass controller to the view
        new MainWindow(chatController);
    }
}
