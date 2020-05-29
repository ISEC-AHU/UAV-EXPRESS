package com.anhui.delivery.service;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.anhui.delivery.dao.UAVDao;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.User;

public class UAVService {
	
	private UAVDao uavDao = new UAVDao();
	
	public List<DeliveryTask> getDeliveryTasks(String uav_id) throws UnknownHostException{
		List<DeliveryTask>   deliveryTasks=new ArrayList<DeliveryTask>();
		deliveryTasks=uavDao.getDeliveryTasks(uav_id);
		return deliveryTasks;
	}
	
	public Order findWaitingOrders(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Order order=new Order();
		order=uavDao.findWaitingOrders(order_id);
		return order;
	}
	
	public void startDelivery(String order_id,String time,String location1) throws UnknownHostException{
		this.uavDao.startDelivery(order_id,time,location1);
	}
	
	public DeliveryTask getTask(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return uavDao.getTask(order_id);
	}
	
	public void saveDeliveryProcess(String order_id,String time,String currentLoc,String deliveryState) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.uavDao.saveDeliveryProcess(order_id,time,currentLoc,deliveryState);
	}
	
	public void updateOrderState(String order_id,String deliveryState) {
		try {
			this.uavDao.updateOrderState(order_id,deliveryState);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User getReceiverByOrder_id(String order_id) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, UnknownHostException{
		return this.uavDao.getReceiverByOrder_id(order_id);
	}


}
