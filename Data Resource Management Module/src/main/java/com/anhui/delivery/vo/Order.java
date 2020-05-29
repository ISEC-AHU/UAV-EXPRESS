package com.anhui.delivery.vo;

import com.anhui.delivery.utils.JsonUtils;
import com.mongodb.DBObject;

public class Order {
	String order_id;
	String receiverName;
	String receriverAddr;
	String packageLoad;
	String state;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceriverAddr() {
		return receriverAddr;
	}
	public void setReceriverAddr(String receriverAddr) {
		this.receriverAddr = receriverAddr;
	}
	public String getPackageLoad() {
		return packageLoad;
	}
	public void setPackageLoad(String packageLoad) {
		this.packageLoad = packageLoad;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String toJson(){
		return JsonUtils.toJson(this);
	}
	
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", receiverName=" + receiverName + ", receriverAddr=" + receriverAddr
				+ ", packageLoad=" + packageLoad + ", state=" + state + "]";
	}
	
	public void parse(DBObject dbo){
		this.setOrder_id((String)dbo.get("order_id"));
		this.setReceiverName((String)dbo.get("receiverName"));
		this.setReceriverAddr((String)dbo.get("receriverAddr"));
		this.setPackageLoad((String)dbo.get("packageLoad"));
		this.setState((String)dbo.get("state"));
		
	}

}
