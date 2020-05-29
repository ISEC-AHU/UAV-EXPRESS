package com.anhui.delivery.vo;

import com.anhui.delivery.utils.JsonUtils;
import com.mongodb.DBObject;


public class User {
	private String username;
	private String password;
	private String type;
	private String ethAccount;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getEthAccount() {
		return ethAccount;
	}
	public void setEthAccount(String ethAccount) {
		this.ethAccount = ethAccount;
	}
	
	public String toJson(){
		return JsonUtils.toJson(this);
	}

	public void parse(DBObject dbo){
		this.setUsername((String)dbo.get("username"));
		this.setPassword((String)dbo.get("password"));
		this.setType((String)dbo.get("type"));
		this.setEthAccount((String)dbo.get("ethAccount"));
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", type=" + type + ", ethAccount=" + ethAccount
				+ "]";
	}
	


}
