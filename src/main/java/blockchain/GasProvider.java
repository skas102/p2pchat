package blockchain;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

import static blockchain.NotaryContract.*;

public class GasProvider implements ContractGasProvider {
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return BigInteger.valueOf(1);
    }

    @Override
    public BigInteger getGasPrice() {
        return null;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        switch (contractFunc) {
            case FUNC_ADDMESSAGE:
            case FUNC_ACCEPTMESSAGE:
            case FUNC_REJECTMESSAGE:
                return BigInteger.valueOf(70000);
            default:
                return BigInteger.valueOf(21000);
        }
    }

    @Override
    public BigInteger getGasLimit() {
        return null;
    }
}
