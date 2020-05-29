package com.anhui.delivery.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.anhui.delivery.dao.DeliveryStationDao;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.UAV;

public class DeliveryStationService {
	
	private DeliveryStationDao dsDao = new DeliveryStationDao();
	
	
	
	public void save(UAV uav) throws UnknownHostException{
		this.dsDao.save(uav);
	}
	
	public void delete(String uid) throws UnknownHostException{
		this.dsDao.delete(uid);
	}
	
	public void saveOrder(Order order) throws UnknownHostException{
		this.dsDao.saveOrder(order);
	}
	
	public void orderUAVSchedule(DeliveryTask deliveryTask) throws UnknownHostException{
		this.dsDao.orderUAVSchedule(deliveryTask);
	}
	
	
	public List<UAV> findUncheckedUAV(String registrant) throws UnknownHostException{
		List<UAV>   uavList=new ArrayList<UAV>();
		uavList=dsDao.findUncheckedUAV(registrant);
		return uavList;
	}
	
	public List<UAV> findCertifiedUAV(String registrant) throws UnknownHostException{
		List<UAV>   uavList=new ArrayList<UAV>();
		uavList=dsDao.findCertifiedUAV(registrant);
		return uavList;
	}
	
	public List<UAV> findDeniedUAV(String registrant) throws UnknownHostException{
		List<UAV>   uavList=new ArrayList<UAV>();
		uavList=dsDao.findDeniedUAV(registrant);
		return uavList;
	}
	
	public void deleteTask() throws UnknownHostException{
		this.dsDao.deleteTask();
	}
	public void deleteOrder(String order_id) throws UnknownHostException{
		this.dsDao.deleteOrder(order_id);
	}

}
