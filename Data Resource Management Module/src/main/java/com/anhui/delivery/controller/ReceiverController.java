package com.anhui.delivery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anhui.delivery.service.ReceiverService;
import com.anhui.delivery.vo.DeliveryProcess;
import com.anhui.delivery.vo.DeliveryTask;

@Controller
@Scope(value="prototype")
@RequestMapping("receiver")
public class ReceiverController {
	
	private ReceiverService receiverService=new ReceiverService();
	
	
	@RequestMapping(value="/deliveryDetails")
	public String getDeliveryDetails(@RequestParam(value="order_id") String order_id, Map<String, Object> map) throws Exception{
		List<DeliveryProcess> processOfDelivery=new ArrayList<DeliveryProcess>();
		DeliveryTask deliveryTask=receiverService.getDeliveryTask(order_id);
		String deliveryDetails=deliveryTask.getDelviery_process();
		String uav_id=deliveryTask.getUav_id();
		/*System.out.println();
		System.out.println("deliveryDetails:"+deliveryDetails);*/
		String[] process1=deliveryDetails.split("\\."); 
		for(int i=0;i<process1.length;i++) {
			String[] process2=process1[i].split("\\|"); //process2[0]:time  process2[1]:location   process2[2]:status
			DeliveryProcess deliveryProcess=new DeliveryProcess();
		    deliveryProcess.setTime(process2[0]);
			deliveryProcess.setLocation(process2[1]);
			deliveryProcess.setState(process2[2]);
			processOfDelivery.add(deliveryProcess);
		}
		map.put("order_id", order_id);
		map.put("uav_id", uav_id);
		map.put("processOfDelivery", processOfDelivery);
		return "jsp/receiver_deliveryDetails";
		
	}

}
