package com.anhui.delivery.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class CertifiedUAV extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506109ba806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80635823dac3146100465780639341bc0014610175578063ee47c86514610270575b600080fd5b6101736004803603604081101561005c57600080fd5b81019060208101813564010000000081111561007757600080fd5b82018360208201111561008957600080fd5b803590602001918460018302840111640100000000831117156100ab57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156100fe57600080fd5b82018360208201111561011057600080fd5b8035906020019184600183028401116401000000008311171561013257600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061038b945050505050565b005b6101926004803603602081101561018b57600080fd5b50356105ce565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101d35781810151838201526020016101bb565b50505050905090810190601f1680156102005780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561023357818101518382015260200161021b565b50505050905090810190601f1680156102605780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b6103166004803603602081101561028657600080fd5b8101906020810181356401000000008111156102a157600080fd5b8201836020820111156102b357600080fd5b803590602001918460018302840111640100000000831117156102d557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610719945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610350578181015183820152602001610338565b50505050905090810190601f16801561037d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60006001836040518082805190602001908083835b602083106103bf5780518252601f1990920191602091820191016103a0565b51815160001960209485036101000a0190811690199190911617905292019485525060405193849003019092205492505050806104735760005460405184516001918691819060208401908083835b6020831061042d5780518252601f19909201916020918201910161040e565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922092909255505060008054906104709060018301610841565b90505b6040518060400160405280848152602001838152506000828154811061049557fe5b906000526020600020906002020160008201518160000190805190602001906104bf929190610872565b5060208281015180516104d89260018501920190610872565b50905050826040518082805190602001908083835b6020831061050c5780518252601f1990920191602091820191016104ed565b51815160209384036101000a60001901801990921691161790526040805192909401829003822081835288518383015288519096507fba8355632a3e8e3f0c2ef849ee523d220198814ca29fbf587297818b69cc834d95508894929350839283019185019080838360005b8381101561058f578181015183820152602001610577565b50505050905090810190601f1680156105bc5780820380516001836020036101000a031916815260200191505b509250505060405180910390a2505050565b600081815481106105db57fe5b60009182526020918290206002918202018054604080516001831615610100026000190190921693909304601f8101859004850282018501909352828152909350918391908301828280156106715780601f1061064657610100808354040283529160200191610671565b820191906000526020600020905b81548152906001019060200180831161065457829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561070f5780601f106106e45761010080835404028352916020019161070f565b820191906000526020600020905b8154815290600101906020018083116106f257829003601f168201915b5050505050905082565b606060006001836040518082805190602001908083835b6020831061074f5780518252601f199092019160209182019101610730565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600080549194509250839150811061078f57fe5b90600052602060002090600202016001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108345780601f1061080957610100808354040283529160200191610834565b820191906000526020600020905b81548152906001019060200180831161081757829003601f168201915b5050505050915050919050565b81548183558181111561086d5760020281600202836000526020600020918201910161086d91906108f0565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106108b357805160ff19168380011785556108e0565b828001600101855582156108e0579182015b828111156108e05782518255916020019190600101906108c5565b506108ec929150610924565b5090565b61092191905b808211156108ec57600061090a828261093e565b61091860018301600061093e565b506002016108f6565b90565b61092191905b808211156108ec576000815560010161092a565b50805460018160011615610100020316600290046000825580601f106109645750610982565b601f0160209004906000526020600020908101906109829190610924565b5056fea265627a7a72315820e4f8faa50c38065c63f500f9dc2cd0464daa0f21d9d24398708c90236752a95064736f6c63430005110032";

    public static final String FUNC_DRONES = "drones";

    public static final String FUNC_GETCERTIFIEDUAVBYID = "getCertifiedUAVById";

    public static final String FUNC_SAVECERTIFIEDUAV = "saveCertifiedUAV";

    public static final Event SAVECERTIFIEDUAV_EVENT = new Event("SaveCertifiedUAV", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public CertifiedUAV(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
        
    }

    public CertifiedUAV(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<SaveCertifiedUAVEventResponse> getSaveCertifiedUAVEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SAVECERTIFIEDUAV_EVENT, transactionReceipt);
        ArrayList<SaveCertifiedUAVEventResponse> responses = new ArrayList<SaveCertifiedUAVEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SaveCertifiedUAVEventResponse typedResponse = new SaveCertifiedUAVEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._uid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._uavLink = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SaveCertifiedUAVEventResponse> saveCertifiedUAVEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SaveCertifiedUAVEventResponse>() {
            @Override
            public SaveCertifiedUAVEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SAVECERTIFIEDUAV_EVENT, log);
                SaveCertifiedUAVEventResponse typedResponse = new SaveCertifiedUAVEventResponse();
                typedResponse.log = log;
                typedResponse._uid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._uavLink = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SaveCertifiedUAVEventResponse> saveCertifiedUAVEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SAVECERTIFIEDUAV_EVENT));
        return saveCertifiedUAVEventObservable(filter);
    }

    public RemoteCall<Tuple2<String, String>> drones(BigInteger param0) {
        final Function function = new Function(FUNC_DRONES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> getCertifiedUAVById(String _uid) {
        final Function function = new Function(
                FUNC_GETCERTIFIEDUAVBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> saveCertifiedUAV(String _uid, String _uavLink) {
        final Function function = new Function(
                FUNC_SAVECERTIFIEDUAV, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uid), 
                new org.web3j.abi.datatypes.Utf8String(_uavLink)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<CertifiedUAV> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertifiedUAV.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CertifiedUAV> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertifiedUAV.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static CertifiedUAV load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertifiedUAV(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CertifiedUAV load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertifiedUAV(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SaveCertifiedUAVEventResponse {
        public Log log;

        public byte[] _uid;

        public String _uavLink;
    }
}
