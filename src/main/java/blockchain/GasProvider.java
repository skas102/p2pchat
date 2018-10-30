package blockchain;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class GasProvider implements ContractGasProvider {
    public static final String LOAD = "load";

    // todo
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        switch (contractFunc) {
            case LOAD:
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
            case LOAD:
                return BigInteger.valueOf(2300);
            default:
                return BigInteger.valueOf(2300);
        }
    }

    @Override
    public BigInteger getGasLimit() {
        return null;
    }
}
