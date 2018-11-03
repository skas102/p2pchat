package services;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public interface NotaryService {
    void start() throws IOException, CipherException;

    CompletableFuture<TransactionReceipt> addMessageHash(byte[] hash);

    CompletableFuture<TransactionReceipt> acceptMessage(byte[] hash);

    CompletableFuture<TransactionReceipt> rejectMessage(byte[] hash);

    BigInteger getMessageState(byte[] hash) throws Exception;
}
