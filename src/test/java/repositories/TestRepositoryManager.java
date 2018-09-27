package repositories;

import models.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestRepositoryManager {

    @Test
    public void testSave() throws IOException {
        ChatRepository repo = new ChatRepository(new User(4000));
        RepositoryManager.save(repo);
    }

    @Test
    public void testLoad() throws IOException, ClassNotFoundException {
        ChatRepository repo = new ChatRepository(new User(4000));
        RepositoryManager.save(repo);

        ChatRepository repo2 = RepositoryManager.load(repo.getProfileName());
        Assert.assertEquals("User4000", repo2.getProfileName());
    }
}
