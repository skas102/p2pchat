package blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class NotaryContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060028054600160a060020a031916331790556103a5806100326000396000f30060806040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630766a799811461007157806341c0e1b51461008b57806377ffe162146100a05780639241f0bc146100b857806392b2060c146100f4575b600080fd5b34801561007d57600080fd5b50610089600435610125565b005b34801561009757600080fd5b506100896101c5565b3480156100ac57600080fd5b50610089600435610202565b3480156100c457600080fd5b506100d060043561029f565b604051808260028111156100e057fe5b60ff16815260200191505060405180910390f35b34801561010057600080fd5b5061008960043573ffffffffffffffffffffffffffffffffffffffff602435166102b4565b60008181526020819052604090205460ff61010090910416151560011461014b57600080fd5b60008181526020819052604081205460ff16600281111561016857fe5b1461017257600080fd5b60008181526001602052604090205473ffffffffffffffffffffffffffffffffffffffff1633146101a257600080fd5b600081815260208190526040902080546001919060ff191682805b021790555050565b60025473ffffffffffffffffffffffffffffffffffffffff163314156102005760025473ffffffffffffffffffffffffffffffffffffffff16ff5b565b60008181526020819052604090205460ff61010090910416151560011461022857600080fd5b60008181526020819052604081205460ff16600281111561024557fe5b1461024f57600080fd5b60008181526001602052604090205473ffffffffffffffffffffffffffffffffffffffff16331461027f57600080fd5b600081815260208190526040902080546002919060ff19166001836101bd565b60009081526020819052604090205460ff1690565b600082815260208190526040902054610100900460ff16156102d557600080fd5b60408051808201825260008082526001602080840182905286835282905292902081518154929391929091839160ff19169083600281111561031357fe5b0217905550602091820151815461ff001916610100911515919091021790556000928352600190526040909120805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff9092169190911790555600a165627a7a72305820b1e680ccd743589f76234ffabb1ea0d6118a41eb34a0e44adf0a6adaf010b5c60029";

    public static final String FUNC_ACCEPTMESSAGE = "acceptMessage";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_REJECTMESSAGE = "rejectMessage";

    public static final String FUNC_GETMESSAGESTATE = "getMessageState";

    public static final String FUNC_ADDMESSAGE = "addMessage";

    @Deprecated
    protected NotaryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NotaryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NotaryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NotaryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> acceptMessage(byte[] hash) {
        final Function function = new Function(
                FUNC_ACCEPTMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rejectMessage(byte[] hash) {
        final Function function = new Function(
                FUNC_REJECTMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getMessageState(byte[] hash) {
        final Function function = new Function(FUNC_GETMESSAGESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addMessage(byte[] hash, String recipient) {
        final Function function = new Function(
                FUNC_ADDMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.Address(recipient)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<NotaryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NotaryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<NotaryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NotaryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NotaryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NotaryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NotaryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NotaryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static NotaryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotaryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NotaryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotaryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NotaryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NotaryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NotaryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NotaryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
