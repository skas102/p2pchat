import controllers.ChatController;
import dtos.PersonDTO;
import models.BootstrapPeer;
import models.Client;
import org.web3j.crypto.CipherException;
import repositories.ChatRepository;
import services.*;
import util.ChatLogger;
import views.MainWindow;

import java.io.IOException;

public class ChatApplication {
    private P2PService p2pService;
    private NotaryService notaryService;
    private ChatService chatService;
    private ChatController chatController;

    public ChatApplication(Client client, BootstrapPeer bootstrapPeer) {
        p2pService = new TomP2PService(client, bootstrapPeer);
        notaryService = new EthereumNotaryService();
        // TODO set contract address in notary Service

        // todo ask client for the username if first time, otherwise load from data file
        ChatRepository repo = new ChatRepository(client);
        chatService = new P2PChatService(p2pService, repo);
        chatController = new ChatController(chatService);
    }

    public void run() {
        try {
            PersonDTO self = p2pService.start();
            notaryService.start();

            // todo remove: test contract call
            notaryService.getMessageState("B94D27B9934D3E08A52E52D7DA7DABFAC484EFE37A5380EE9088F7ACE2EFCDE9");

            chatController.setSelf(self);
            p2pService.receiveMessage(chatService);
        } catch (InterruptedException | IOException ex) {
            System.err.println("Starting P2P Service failed - " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        } catch (CipherException e) {
            ChatLogger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // todo pass controller to the view
        new MainWindow(chatController);
    }
}
