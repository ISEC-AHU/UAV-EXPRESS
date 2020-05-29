package com.anhui.delivery.vo;

public class DeliveryProcess {
	private String time;
	private String location;
	private String state;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "DeliveryProcess [time=" + time + ", location=" + location + ", state=" + state + "]";
	}
	
	

}
