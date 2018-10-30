package services;

import blockchain.NotaryContractGasProvider;
import blockchain.NotaryServiceContract;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import util.ChatLogger;

import java.io.IOException;

// TODO We need to deploy the smart blockchain to the ropsten testnet and store the blockchain address somewhere.
// TODO generate a solidity contract interface with the command line tool (maybe)

public class EthereumNotaryService implements NotaryService {

    private String contractAddress = "0x20345ec4429c268a4dfa330a57561001b63b7df6";
    private NotaryContractGasProvider gasProvider;
    private Credentials credentials;
    private NotaryServiceContract contract;

    // TODO Add constructor that takes a contract address and creates instance of Contract
    public EthereumNotaryService(){
        gasProvider = new NotaryContractGasProvider();
    }

    @Override
    public void start() throws IOException, CipherException {
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/96269096d50040d39e625e2241929596"));
        ChatLogger.info("Connected to Ethereum client " +
                web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // This is the local wallet for every node. It is required to communicate with the smart contract
        // Hint: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
        String walletPath = System.getProperty("user.home") + "/wallet.json";

        // TODO: store password somewhere else
        credentials = WalletUtils.loadCredentials("test123", walletPath);
        ChatLogger.info("Credentials loaded. Address: " + credentials.getAddress());

        contract = NotaryServiceContract.load(contractAddress, web3j, credentials, gasProvider);
        ChatLogger.info("NotaryServiceContract is loaded");
    }

    public void requestEthersFromTestnet() {
        // FIXME: Request some Ether for the Ropsten test network
        ChatLogger.info("Sending 1 Wei ("
            + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");

//        TransactionReceipt transferReceipt = Transfer.sendFunds(
//            web3j, credentials,
//            "0x687422eea2cb73b5d3e242ba5456b782919afc85",  // you can put any address here
//            BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
//            .send();
//
//        ChatLogger.info("Transaction complete, view it at https://ropsten.etherscan.io/"
//            + transferReceipt.getTransactionHash());
    }

    public void setContractAddress(String address) {
        // TODO Implement
    }

    public void addMessageHash(String hash, String recipient) {
        // TODO Implement
    }

    public void acceptMessage(String hash) {
        // TODO Implement
    }

    public void rejectMessage(String hash) {
        // TODO Implement
    }

    public void getMessageState(String hash) {
        
    }
}
