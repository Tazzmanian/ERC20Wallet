package wallet.erc20.tazzmanian.erc20wallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import rx.Observable;
import rx.functions.Func1;


public class ContractWrapper extends Contract {

    private static final String BINARY = "60806040523480156200001157600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506040805190810160405280600581526020017f4649584544000000000000000000000000000000000000000000000000000000815250600290805190602001906200009f92919062000221565b506040805190810160405280601a81526020017f4578616d706c6520466978656420537570706c7920546f6b656e00000000000081525060039080519060200190620000ed92919062000221565b506012600460006101000a81548160ff021916908360ff160217905550600460009054906101000a900460ff1660ff16600a0a620f424002600581905550600554600660008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef6005546040518082815260200191505060405180910390a3620002d0565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200026457805160ff191683800117855562000295565b8280016001018555821562000295579182015b828111156200029457825182559160200191906001019062000277565b5b509050620002a49190620002a8565b5090565b620002cd91905b80821115620002c9576000816000905550600101620002af565b5090565b90565b61150f80620002e06000396000f3006080604052600436106100e6576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100eb578063095ea7b31461017b57806318160ddd146101e057806323b872dd1461020b578063313ce567146102905780633eaaf86b146102c157806370a08231146102ec57806379ba5097146103435780638da5cb5b1461035a57806395d89b41146103b1578063a9059cbb14610441578063cae9ca51146104a6578063d4ee1d9014610551578063dc39d06d146105a8578063dd62ed3e1461060d578063f2fde38b14610684575b600080fd5b3480156100f757600080fd5b506101006106c7565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610140578082015181840152602081019050610125565b50505050905090810190601f16801561016d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018757600080fd5b506101c6600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610765565b604051808215151515815260200191505060405180910390f35b3480156101ec57600080fd5b506101f5610857565b6040518082815260200191505060405180910390f35b34801561021757600080fd5b50610276600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506108a2565b604051808215151515815260200191505060405180910390f35b34801561029c57600080fd5b506102a5610b4d565b604051808260ff1660ff16815260200191505060405180910390f35b3480156102cd57600080fd5b506102d6610b60565b6040518082815260200191505060405180910390f35b3480156102f857600080fd5b5061032d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610b66565b6040518082815260200191505060405180910390f35b34801561034f57600080fd5b50610358610baf565b005b34801561036657600080fd5b5061036f610d4e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156103bd57600080fd5b506103c6610d73565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156104065780820151818401526020810190506103eb565b50505050905090810190601f1680156104335780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561044d57600080fd5b5061048c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610e11565b604051808215151515815260200191505060405180910390f35b3480156104b257600080fd5b50610537600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610fac565b604051808215151515815260200191505060405180910390f35b34801561055d57600080fd5b506105666111fb565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156105b457600080fd5b506105f3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611221565b604051808215151515815260200191505060405180910390f35b34801561061957600080fd5b5061066e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611385565b6040518082815260200191505060405180910390f35b34801561069057600080fd5b506106c5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061140c565b005b60038054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561075d5780601f106107325761010080835404028352916020019161075d565b820191906000526020600020905b81548152906001019060200180831161074057829003601f168201915b505050505081565b600081600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b6000600660008073ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205460055403905090565b60006108f682600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546114ab90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506109c882600760008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546114ab90919063ffffffff16565b600760008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610a9a82600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546114c790919063ffffffff16565b600660008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a3600190509392505050565b600460009054906101000a900460ff1681565b60055481565b6000600660008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c0b57600080fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e095780601f10610dde57610100808354040283529160200191610e09565b820191906000526020600020905b815481529060010190602001808311610dec57829003601f168201915b505050505081565b6000610e6582600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546114ab90919063ffffffff16565b600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610efa82600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546114c790919063ffffffff16565b600660008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a36001905092915050565b600082600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925856040518082815260200191505060405180910390a38373ffffffffffffffffffffffffffffffffffffffff16638f4ffcb1338530866040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561118957808201518184015260208101905061116e565b50505050905090810190601f1680156111b65780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b1580156111d857600080fd5b505af11580156111ec573d6000803e3d6000fd5b50505050600190509392505050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561127e57600080fd5b8273ffffffffffffffffffffffffffffffffffffffff1663a9059cbb6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff16846040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b15801561134257600080fd5b505af1158015611356573d6000803e3d6000fd5b505050506040513d602081101561136c57600080fd5b8101908080519060200190929190505050905092915050565b6000600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561146757600080fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60008282111515156114bc57600080fd5b818303905092915050565b600081830190508281101515156114dd57600080fd5b929150505600a165627a7a723058206d131c063b5ff27910de06b3c35343f841065ab79bc312fa0bcf1d381f0615ed0029";

    protected ContractWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, gasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<Utf8String> name() throws IOException {
        Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt approve(Address _spender, Uint256 _value) throws IOException, TransactionException {
        Function function = new Function("approve", Arrays.<Type>asList(_spender, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint256> totalSupply() throws IOException {
        Function function = new Function("totalSupply",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt transferFrom(Address _from, Address _to, Uint256 _value) throws IOException, TransactionException {
        Function function = new Function("transferFrom", Arrays.<Type>asList(_from, _to, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint256> INITIAL_SUPPLY() throws IOException {
        Function function = new Function("INITIAL_SUPPLY",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturn(function);
    }

    public Uint8 decimals() throws IOException {
        Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeCallSingleValueReturn(function);
    }

//    public TransactionReceipt SimpleToken() throws IOException, TransactionException {
//        Function function = new Function("SimpleToken", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
//        return executeTransaction(function);
//    }

//    public Future<TransactionReceipt> decreaseApproval(Address _spender, Uint256 _subtractedValue) {
//        Function function = new Function("decreaseApproval", Arrays.<Type>asList(_spender, _subtractedValue), Collections.<TypeReference<?>>emptyList());
//        return executeTransactionAsync(function);
//    }

    public Future<Uint256> balanceOf(Address _owner) throws IOException {
        Function function = new Function("balanceOf",
                Arrays.<Type>asList(_owner),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturn(function);
    }

    public Future<Utf8String> symbol() throws IOException {
        Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt transfer(Address _to, Uint256 _value) throws IOException, TransactionException {
        Function function = new Function("transfer", Arrays.<Type>asList(_to, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public TransactionReceipt increaseApproval(Address _spender, Uint256 _addedValue) throws IOException, TransactionException {
        Function function = new Function("increaseApproval", Arrays.<Type>asList(_spender, _addedValue), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint256> allowance(Address _owner, Address _spender) throws IOException {
        Function function = new Function("allowance",
                Arrays.<Type>asList(_owner, _spender),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturn(function);
    }

    public static ContractWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        return new ContractWrapper(contractAddress, web3j, transactionManager, gasProvider);
    }


    public static class ApprovalEventResponse {
        public Address owner;

        public Address spender;

        public Uint256 value;
    }

    public static class TransferEventResponse {
        public Address from;

        public Address to;

        public Uint256 value;
    }

}
