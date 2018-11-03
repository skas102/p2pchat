package services;

import blockchain.GasProvider;
import blockchain.NotaryContract;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import util.ChatLogger;
import util.StringUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public class EthereumNotaryService implements NotaryService {

    private final String contractAddress = "0x59cd1f59b9cf96baaeea7a1b82e73d1f5d1831ad";
    private final String username;
    private GasProvider gasProvider;
    private Credentials credentials;
    private NotaryContract contract;

    public EthereumNotaryService(String username) {
        gasProvider = new GasProvider();
        this.username = username;
    }

    @Override
    public void start() throws IOException, CipherException {
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/96269096d50040d39e625e2241929596"));
        ChatLogger.info("Connected to Ethereum client " +
                web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // This is the local wallet for every node. It is required to communicate with the smart contract
        // Hint: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
        String walletPath = "wallets/wallet_" + username + ".json";

        // It is not good to save password and wallet in the repo
        // But yeah, just for the purpose of education!!!
        credentials = WalletUtils.loadCredentials("test123", walletPath);
        ChatLogger.info("Credentials loaded. Address: " + credentials.getAddress());

        contract = NotaryContract.load(contractAddress, web3j, credentials, gasProvider);
        ChatLogger.info("NotaryContract is loaded");
    }

    public CompletableFuture<TransactionReceipt> addMessageHash(byte[] hash) {
        CompletableFuture<TransactionReceipt> txFuture = contract.addMessage(hash).sendAsync();
        ChatLogger.info("Transaction started: call addMessageHash with " + StringUtil.bytesToHex(hash));

        return txFuture;
    }

    public CompletableFuture<TransactionReceipt> acceptMessage(byte[] hash) {
        CompletableFuture<TransactionReceipt> txFuture = contract.acceptMessage(hash).sendAsync();
        ChatLogger.info(String.format("Transaction started: call acceptMessage with hash=%s",
                StringUtil.bytesToHex(hash)));

        return txFuture;
    }

    public CompletableFuture<TransactionReceipt> rejectMessage(byte[] hash){
        CompletableFuture<TransactionReceipt> txFuture = contract.rejectMessage(hash).sendAsync();
        ChatLogger.info(String.format("Transaction started: call rejectMessage with hash=%s",
                StringUtil.bytesToHex(hash)));

        return txFuture;
    }

    // To test:
    // hash "0666BDA53FB35307856D288BB7B74C87E96949D4CC821242A7FFB8EDF2D014E2" --> accepted
    // hash "9C6F30444092D79F5763EF181BAFDCF31DFDD070D471E5A03BB1E43EC6519694" --> rejected
    public void getMessageState(String hash) throws Exception {
        BigInteger state = contract.getMessageState(StringUtil.hexToByteArray(hash)).send();
        ChatLogger.info("State: " + state);
    }
}
