package com.anhui.delivery.service;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.anhui.delivery.contract.CertifiedUAV;
import com.anhui.delivery.contract.DetectDeliveryProcess;
import com.anhui.delivery.utils.BlockchainUtil;

public class UavBcService {
	private CertifiedUAV UAVContract;
	private DetectDeliveryProcess deliveryProcessContract;


    public UavBcService(){
        Credentials credentials =  Credentials.create("0x220e7ed7d9be09e0e9e3cb9b740c9d5e1c3e46e15ba4eb0b0c51ee6e5849769a"); 
        UAVContract = BlockchainUtil.getCertifiedUAVContract(credentials,"0x529E09627d8831e32bd23c1656DaC4caCA4b5f50");
  	    deliveryProcessContract= BlockchainUtil.getDeliveryProcessContract(credentials, "0x83A5201Ffec4c5358dFcadFADB26fEEe174e22Cf");
    }
    
    
    public TransactionReceipt saveCerifiedUAVOnBc(String uid, String uavLink) throws Exception{
    	
        return UAVContract.saveCertifiedUAV(uid, uavLink).send();
    }
    
   public TransactionReceipt setNegotiateResult(String order_id, String _receiverEthAddr,BigInteger _deliveryPrice,BigInteger _penaltyPrice,String time) throws Exception{
    	
        return deliveryProcessContract.setNegotiateResult(order_id, _receiverEthAddr, _deliveryPrice, _penaltyPrice, time).send();
    }
   
   public TransactionReceipt saveReceipt(String order_id, String _imageReceipt) throws Exception{
   	
	    
       return deliveryProcessContract.saveReceipt(order_id, _imageReceipt).send();
   }
   
   public TransactionReceipt recordDeliveryProcess(String _droneUid, String _uavEthAccount,String _orderID,String _location,String _time,String _state) throws Exception{
	   TransactionReceipt transactionReceipt =deliveryProcessContract.recordDeliveryProcess(_droneUid, _uavEthAccount, _orderID, _location, _time, _state).send();
	   //System.out.println();
   	 //System.out.println("交易收据为："+transactionReceipt);
	   return transactionReceipt;
   }
    
   public BigInteger generateCode(String order_id) throws Exception {
    	
	    deliveryProcessContract.generateCode(order_id).send();
    	BigInteger randomCode=deliveryProcessContract.getRandomCode(order_id).send();
    	return randomCode;
    	
    }
   
 /*  public void generateCodeEventObservable(EthFilter filter) {
	  System.out.println();
	   deliveryProcessContract.generateCodeEventObservable(filter);
   }*/
  
    
    /*public TransactionReceipt addMsg(String username, String ID, String violateRecord, BigInteger lowPoint, String AdministratorName) throws Exception{
        return contract.addMsg(username, ID, violateRecord, lowPoint, AdministratorName).send();
    }

    public BigInteger returnTotal() throws Exception {
        return contract.returnTotal().send().getValue();
    }

    public String getuserName(BigInteger id) throws Exception {
        return contract.getuserName(id).send().getValue();
    }

    public String getID(BigInteger id) throws Exception{
        return contract.getID(id).send().getValue();
    }

    public String getviolateRecord(BigInteger id) throws Exception{
        return contract.getviolateRecord(id).send().getValue();
    }

    public String getlowPoint(BigInteger id) throws Exception{
        return contract.getlowPoint(id).send().getValue().toString();
    }

    public String getAdministrator(BigInteger id) throws Exception{
        return contract.getAdministrator(id).send().getValue();
    }
    */
   /* public TransactionReceipt saveCerifiedUAVOnBc(String uid, String uavLink) throws Exception{
        return contract.addMsg(username, ID, violateRecord, lowPoint, AdministratorName).send();
    }
*/

}
