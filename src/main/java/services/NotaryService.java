package services;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface NotaryService {
    void start() throws IOException, CipherException;

    CompletableFuture<TransactionReceipt> addMessageHash(byte[] hash);

    void acceptMessage(String hash) throws Exception;

    void rejectMessage(String hash) throws Exception;

    void getMessageState(String hash) throws Exception;
}
