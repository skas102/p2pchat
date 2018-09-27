import controllers.ChatController;
import models.User;
import repositories.ChatRepository;
import services.P2PService;
import services.TomP2PService;
import views.MainWindow;

import java.io.IOException;

public class ChatApplication {
    private P2PService p2pService;
    private ChatController chatController;
    private User user;

    public ChatApplication(User user) {
        p2pService = new TomP2PService(user);

        // todo ask user for the username if first time, otherwise load from data file
        ChatRepository repo = new ChatRepository(user);
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
        new MainWindow();
    }
}
