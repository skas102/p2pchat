package repositories;

import java.io.*;

public class RepositoryManager {

    public static ChatRepository load(String username) throws IOException, ClassNotFoundException {
        try (FileInputStream fs = new FileInputStream(username)) {
            try (ObjectInputStream objStream = new ObjectInputStream(fs)) {
                return (ChatRepository) objStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void save(ChatRepository repo) throws IOException {
        try (FileOutputStream fs = new FileOutputStream(repo.getProfileName(), false)) {
            try (ObjectOutputStream objStream = new ObjectOutputStream(fs)) {
                objStream.writeObject(repo);

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}