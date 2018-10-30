package services;

import org.web3j.crypto.CipherException;

import java.io.IOException;

public interface NotaryService {
    void start() throws IOException, CipherException;
}
