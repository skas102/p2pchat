package services;

import org.web3j.crypto.CipherException;

import java.io.IOException;

public interface NotaryService {
    void start() throws IOException, CipherException;

    void addMessageHash(String hash) throws Exception;

    void acceptMessage(String hash) throws Exception;

    void rejectMessage(String hash) throws Exception;

    void getMessageState(String hash) throws Exception;
}
