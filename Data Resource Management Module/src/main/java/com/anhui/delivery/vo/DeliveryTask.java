package com.anhui.delivery.vo;

import com.anhui.delivery.utils.JsonUtils;
import com.mongodb.DBObject;

public class DeliveryTask {
	
	String order_id;
	String uav_id; //sender_id
	//String relayUav_id;
	String delviery_process; //时间|位置|状态|.时间|位置|状态
	String route;//位置A|位置B|位置C|目的地
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getUav_id() {
		return uav_id;
	}
	public void setUav_id(String uav_id) {
		this.uav_id = uav_id;
	}
	public String getDelviery_process() {
		return delviery_process;
	}
	public void setDelviery_process(String delviery_process) {
		this.delviery_process = delviery_process;
	}
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String toJson(){
		return JsonUtils.toJson(this);
	}
	
	@Override
	public String toString() {
		return "DeliveryTask [order_id=" + order_id + ", uav_id=" + uav_id + ", delviery_process=" + delviery_process
				+ ", route=" + route + "]";
	}
	
	public void parse(DBObject dbo){
		this.setOrder_id((String)dbo.get("order_id"));
		this.setUav_id((String)dbo.get("uav_id"));
		this.setDelviery_process((String)dbo.get("delviery_process"));
		this.setRoute((String)dbo.get("route"));
	}
	
	

}
