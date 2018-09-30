package services;

import dtos.UserDTO;
import models.BootstrapPeer;
import models.Client;
import org.junit.Assert;
import org.junit.Test;
import util.ChatLogger;

import java.io.IOException;

public class TestTomP2PService {

    @Test
    public void testGetUserOwn() throws IOException, ClassNotFoundException, InterruptedException {
        Client client1 = new Client(4000);
        TomP2PService service = new TomP2PService(client1, null);
        service.start();

        UserDTO userDTO = service.getUser(client1.getUsername());
        Assert.assertEquals(client1.getUsername(), userDTO.getUsername());

        service.shutdown();
    }

    @Test
    public void testGetUserOther() throws IOException, InterruptedException, ClassNotFoundException {
        Client client1 = new Client(4000);
        Client client2 = new Client(4001);

        TomP2PService service1 = new TomP2PService(client1, null);
        service1.start();

        TomP2PService service2 = new TomP2PService(client2, new BootstrapPeer("127.0.0.1", 4000));
        service2.start();

        assertUser(getUser(service1, client2.getUsername(), 10), client2.getUsername());
        assertUser(getUser(service2, client1.getUsername(), 10), client1.getUsername());

        service1.shutdown();
        service2.shutdown();
    }

    private void assertUser(UserDTO user, String expectedUsername) {
        if (user == null) {
            Assert.fail(expectedUsername + " couldn't be retrieved from DHT");
        } else {
            Assert.assertEquals(expectedUsername, user.getUsername());
        }
    }

    private UserDTO getUser(TomP2PService service, String username, int retries) throws IOException, ClassNotFoundException, InterruptedException {
        UserDTO user = null;
        do {
            try {
                Thread.sleep(2000);
                user = service.getUser(username);
                return user;
            } catch (NullPointerException ex) {
                ChatLogger.warn("Client couldn't be retrieved. Remaining retries: " + retries);
                retries--;
            }
        } while (user == null && retries > 0);
        return user;
    }
}
