package com.anhui.delivery.service;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.anhui.delivery.dao.ReceiverDao;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;

public class ReceiverService {
	private ReceiverDao receiverDao = new ReceiverDao();
	
	public List<Order> getAllOrder(String receiverName) throws UnknownHostException{
		List<Order>   orderList=new ArrayList<Order>();
		orderList=receiverDao.getAllOrder(receiverName);
		return orderList;
	}
	
	public DeliveryTask getDeliveryTask(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		return receiverDao.getDeliveryTask(order_id);
	}
	

}
