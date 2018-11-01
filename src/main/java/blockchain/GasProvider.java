package blockchain;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

import static blockchain.NotaryContract.FUNC_ADDMESSAGE;

public class GasProvider implements ContractGasProvider {
    // todo
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        switch (contractFunc) {
            case FUNC_ADDMESSAGE:
                return BigInteger.valueOf(1);
            default:
                return BigInteger.valueOf(1);
        }
    }

    @Override
    public BigInteger getGasPrice() {
        return null;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        switch (contractFunc) {
            case FUNC_ADDMESSAGE:
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
