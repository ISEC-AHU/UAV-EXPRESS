package com.anhui.delivery.controller;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anhui.delivery.service.DeliveryStationService;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.UAV;

@Controller
@Scope(value="prototype")
@RequestMapping("deliverystation")

public class DeliveryStationController   {
	
	private DeliveryStationService dsService = new DeliveryStationService();
	

	@RequestMapping(value="/add")
	public String add()
			throws Exception {
		//dsService.delete("001");
		return "jsp/addUAV";
		
	}
	
	@RequestMapping(value="/addUAV")
	public String addUAV(UAV uav,ModelMap model,HttpServletRequest request,HttpServletResponse response,Writer out,HttpSession session)
			throws Exception {
		uav.setCompanyAccount("0x29E076FE0Ab2adD6E5E51bF25004C811C3e7030C");
		dsService.save(uav);
		
		return "jsp/main_deliveryStation2";
		
	}
	
	@RequestMapping(value="/orderUAVSchedule")
	public String orderUAVSchedule()
			throws Exception {
		
		//dsService.deleteOrder("or_002");
		//dsService.deleteTask();
		Order order3=new Order();
		order3.setOrder_id("or_001");
		order3.setReceiverName("Li Si");
		order3.setReceriverAddr("the north stadium");
		order3.setPackageLoad("3");
		order3.setState("waiting");
		Order order4=new Order();
		order4.setOrder_id("or_002");
		order4.setReceiverName("Li Si");
		order4.setReceriverAddr("the north stadium");
		order4.setPackageLoad("4");
		order4.setState("waiting");
		
		dsService.saveOrder(order3);
		dsService.saveOrder(order4);
		
		
		
		DeliveryTask deliveryTask1=new DeliveryTask();
		deliveryTask1.setOrder_id(order3.getOrder_id());
		deliveryTask1.setUav_id("u_001");
		deliveryTask1.setDelviery_process("");
		deliveryTask1.setRoute("ISEC distribution station"+"|"+"Engineering building D"+"|"+"the north stadium");
		DeliveryTask deliveryTask2=new DeliveryTask();
		deliveryTask2.setOrder_id(order4.getOrder_id());
		deliveryTask2.setUav_id("u_001");
		deliveryTask2.setDelviery_process("");
		deliveryTask2.setRoute("ISEC distribution station"+"|"+"Engineering building D"+"|"+"the north stadium");
		dsService.orderUAVSchedule(deliveryTask1);
		dsService.orderUAVSchedule(deliveryTask2);
		return "";
		
	}
	
}

