import controllers.ChatController;
import dtos.PersonDTO;
import models.BootstrapPeer;
import models.Client;
import repositories.ChatRepository;
import services.ChatService;
import services.P2PChatService;
import services.P2PService;
import services.TomP2PService;
import views.MainWindow;

import java.io.IOException;

public class ChatApplication {
    private P2PService p2pService;
    private ChatService chatService;
    private ChatController chatController;

    public ChatApplication(Client client, BootstrapPeer bootstrapPeer) {
        p2pService = new TomP2PService(client, bootstrapPeer);

        // todo ask client for the username if first time, otherwise load from data file
        ChatRepository repo = new ChatRepository(client);
        chatService = new P2PChatService(p2pService, repo);
        chatController = new ChatController(chatService);
    }

    public void run() {
        try {
            PersonDTO self = p2pService.start();
            chatController.setSelf(self);
            p2pService.receiveMessage(chatService);
        } catch (InterruptedException | IOException ex) {
            System.err.println("Starting P2P Service failed - " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }

        // todo pass controller to the view
        new MainWindow(chatController);
    }
}
