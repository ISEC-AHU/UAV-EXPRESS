package com.anhui.delivery.utils;


import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import com.anhui.delivery.contract.CertifiedUAV;
import com.anhui.delivery.contract.DetectDeliveryProcess;

public class BlockchainUtil {
	
	 // GAS价格
    public static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    // GAS上限
    public static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
	
    public static CertifiedUAV getCertifiedUAVContract(Credentials credentials, String contractAdress){
        Web3j web3j = Web3j.build(new HttpService("http://192.168.184.128:8545"));
        Web3ClientVersion web3ClientVersion;
		try {
			web3ClientVersion = web3j.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
	        //System.out.println(clientVersion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return new CertifiedUAV(contractAdress,web3j,credentials,GAS_PRICE,GAS_LIMIT);
    }
    
    public static DetectDeliveryProcess getDeliveryProcessContract(Credentials credentials, String contractAdress){
        Web3j web3j = Web3j.build(new HttpService("http://192.168.184.128:8545"));
        Web3ClientVersion web3ClientVersion;
		try {
			web3ClientVersion = web3j.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
	        //System.out.println(clientVersion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return new DetectDeliveryProcess(contractAdress,web3j,credentials,GAS_PRICE,GAS_LIMIT);
    }
    
}
