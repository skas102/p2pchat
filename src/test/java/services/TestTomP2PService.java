package services;

import dtos.UserDTO;
import models.User;
import org.junit.Assert;
import org.junit.Test;
import util.ChatLogger;

import java.io.IOException;

public class TestTomP2PService {

    @Test
    public void testGetUserOwn() throws IOException, ClassNotFoundException, InterruptedException {
        User user1 = new User(4000);
        TomP2PService service = new TomP2PService(user1);
        service.start();

        UserDTO userDTO = service.getUser(user1.getUsername());
        Assert.assertEquals(user1.getUsername(), userDTO.getUsername());

        service.shutdown();
    }

    @Test
    public void testGetUserOther() throws IOException, InterruptedException, ClassNotFoundException {
        User user1 = new User(4000);
        User user2 = new User(4001);
        user2.setBootstrapPeer("127.0.0.1", 4000);

        TomP2PService service1 = new TomP2PService(user1);
        service1.start();

        TomP2PService service2 = new TomP2PService(user2);
        service2.start();

        assertUser(getUser(service1, user2.getUsername(), 10), user2.getUsername());
        assertUser(getUser(service2, user1.getUsername(), 10), user1.getUsername());

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
                ChatLogger.warn("User couldn't be retrieved. Remaining retries: " + retries);
                retries--;
            }
        } while (user == null && retries > 0);
        return user;
    }
}
