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

    public void rejectMessage(String hash) throws Exception {
        TransactionReceipt tx = contract.rejectMessage(StringUtil.hexToByteArray(hash)).send();
        ChatLogger.info(String.format("Reject Message Transaction compelted: Status=%s, hash=%s",
                tx.getStatus(), tx.getTransactionHash()));
    }

    // To test: hash "B94D27B9934D3E08A52E52D7DA7DABFAC484EFE37A5380EE9088F7ACE2EFCDE9" <-- "hello world"
    // state = accepted
    public void getMessageState(String hash) throws Exception {
        BigInteger state = contract.getMessageState(StringUtil.hexToByteArray(hash)).send();
        ChatLogger.info("State: " + state);
    }
}
