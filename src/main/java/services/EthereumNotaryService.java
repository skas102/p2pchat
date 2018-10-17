package services;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import util.ChatLogger;

import java.io.IOException;

public class EthereumNotaryService implements NotaryService {
    @Override
    public void start() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/96269096d50040d39e625e2241929596"));
        ChatLogger.info("Connected to Ethereum client " +
                web3j.web3ClientVersion().send().getWeb3ClientVersion());


    }
}
