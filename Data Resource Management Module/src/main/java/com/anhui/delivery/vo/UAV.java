package com.anhui.delivery.vo;

import com.anhui.delivery.utils.JsonUtils;
import com.mongodb.DBObject;

public class UAV {
	
	private String uid;
	private String manufacturer;
	private String companyName;
	private String companyAccount; //以太坊账号
	private String registrant; //注册人
	private String limitedHeight;
	private String limitedLoad;
	private String right;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAccount() {
		return companyAccount;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public String getRegistrant() {
		return registrant;
	}
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	public String getLimitedHeight() {
		return limitedHeight;
	}
	public void setLimitedHeight(String limitedHeight) {
		this.limitedHeight = limitedHeight;
	}
	public String getLimitedLoad() {
		return limitedLoad;
	}
	public void setLimitedLoad(String limitedLoad) {
		this.limitedLoad = limitedLoad;
	}
	
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	 
	public String toJson(){
		return JsonUtils.toJson(this);
	}

	
	public void parse(DBObject dbo){
		this.setUid((String)dbo.get("uid"));
		this.setCompanyName((String)dbo.get("companyName"));
		this.setCompanyAccount((String)dbo.get("companyAccount"));
		this.setManufacturer((String)dbo.get("manufacturer"));
		this.setRegistrant((String)dbo.get("registrant"));
		this.setRight((String)dbo.get("right"));
		this.setLimitedHeight((String)dbo.get("limitedHeight"));
		this.setLimitedLoad((String)dbo.get("limitedLoad"));
		
	}
	@Override
	public String toString() {
		return "UAV [uid=" + uid + ", manufacturer=" + manufacturer + ", companyName=" + companyName
				+ ", companyAccount=" + companyAccount + ", registrant=" + registrant + ", limitedHeight="
				+ limitedHeight + ", limitedLoad=" + limitedLoad + ",  right=" + right + "]";
	}
	
	

}
